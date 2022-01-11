package com.luongtx.oes.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.luongtx.oes.config.ResourcePathConfig;
import com.luongtx.oes.dto.ExamDTO;
import com.luongtx.oes.dto.ExamResultDTO;
import com.luongtx.oes.dto.QuestionDTO;
import com.luongtx.oes.entity.Answer;
import com.luongtx.oes.entity.Exam;
import com.luongtx.oes.entity.Question;
import com.luongtx.oes.entity.User;
import com.luongtx.oes.entity.UserExam;
import com.luongtx.oes.repository.ExamRepo;
import com.luongtx.oes.repository.QuestionRepo;
import com.luongtx.oes.repository.UserExamRepo;
import com.luongtx.oes.repository.UserRepo;
import com.luongtx.oes.repository.specification.QuestionSpecifications;
import com.luongtx.oes.security.utils.JwtTokenUtil;
import com.luongtx.oes.service.ExamService;
import com.luongtx.oes.utils.FileUtils;
import com.luongtx.oes.utils.ImageUtils;
import com.luongtx.oes.utils.converter.AnswerConverter;
import com.luongtx.oes.utils.converter.QuestionConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ExamServiceImpl implements ExamService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ExamRepo examRepo;

    @Autowired
    UserExamRepo userExamRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    ResourcePathConfig pathConfig;

    public List<ExamDTO> findAll() {
        return examRepo.findAll()
                .stream()
                .map(this::convertToExamDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ExamDTO> findAll(Pageable pageable, String searchKey) {
        Page<Exam> pageExams = examRepo.findAll(pageable, searchKey);
        return pageExams.map(this::convertToExamDTO);
    }

    public Exam findById(Long id) {
        return examRepo.findById(id).orElse(new Exam());
    }

    @Override
    public ExamDTO findDetailById(Long id) {
        Exam exam = findById(id);
        int numberOfQuestions = exam.getQuestions().size();
        exam.setNumberOfQuestions(numberOfQuestions);
        return convertToExamDTO(exam);
    }

    @Override
    @Transactional
    public ExamResultDTO evaluateResult(String userToken, Long examId, List<List<Long>> listAnswers) {
        ExamResultDTO resultDTO = new ExamResultDTO();
        Exam exam = examRepo.getById(examId);
        List<Question> questions = exam.getQuestions();
        int corrects = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (listAnswers.get(i).equals(questions.get(i).getCorrectAnswers())) {
                corrects++;
            }
        }
        resultDTO.setScore((100 * corrects) / questions.size());
        resultDTO.setPassingScore(exam.getPassingScore());
        resultDTO.setStatus(resultDTO.getScore() >= exam.getPassingScore());
        resultDTO.setFinishedAt(LocalDateTime.now());
        log.debug(resultDTO);
        updateUserExamResult(userToken, examId, resultDTO);
        return resultDTO;
    }

    @Override
    public List<ExamResultDTO> getRecentUserExams(String userToken) {
        List<ExamResultDTO> examResultDTOS = new ArrayList<>();
        User user = getUserFromToken(userToken);
        Pageable pageable = PageRequest.of(0, 5);
        List<UserExam> userExams = userExamRepo.getMostRecentByUserId(user.getId(), pageable);
        // log.info(userExams.toString());
        for (UserExam userExam : userExams) {
            ExamResultDTO dto = convertToExamResultDTO(userExam);
            examResultDTOS.add(dto);
        }
        return examResultDTOS;
    }

    @Override
    public void save(ExamDTO examDTO) {
        Exam exam = convertToExam(examDTO);
        examRepo.save(exam);
    }

    @Override
    public void save(ExamDTO examDTO, MultipartFile file) {
        Exam exam = convertToExam(examDTO);
        String uploadedFilePath = FileUtils.uploadFile(file, pathConfig.getUploadPath());
        if (uploadedFilePath != null) {
            exam.setBannerImage(uploadedFilePath);
        }
        examRepo.save(exam);
    }

    @Override
    public Long uploadBanner(MultipartFile file, Long examId) {
        Exam exam = new Exam();
        if (examId != null) {
            exam = findById(examId);
        }
        String imageSource = FileUtils.uploadFile(file, pathConfig.getUploadPath());
        exam.setBannerImage(imageSource);
        Exam savedExam = examRepo.save(exam);
        return savedExam.getId();
    }

    @Override
    public void delete(Long id) {
        examRepo.deleteById(id);
    }

    @Override
    public void saveQuestion(QuestionDTO questionDTO, Long examId) {
        Question question = questionRepo.getById(questionDTO.getId());
        question.setContent(questionDTO.getContent());
        List<Answer> answers = questionDTO.getAnswers().stream()
                .map((dto) -> AnswerConverter.convertDTOToEntity(dto, question))
                .collect(Collectors.toList());
        question.setAnswers(answers);
        question.setExam(findById(examId));
        log.debug(question);
        questionRepo.save(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepo.deleteById(questionId);
    }

    @Override
    public Page<QuestionDTO> findAllQuestions(Long examId, Pageable pageable) {
        Specification<Question> specification = QuestionSpecifications.findAllByExam("", examId);
        return questionRepo.findAll(specification, pageable)
                .map(QuestionConverter::convertEntityToDTO);
    }

    public void updateUserExamResult(String userToken, Long examId, ExamResultDTO resultDTO) {
        User user = getUserFromToken(userToken);
        log.info(user.toString());
        UserExam userExam = new UserExam();
        userExam.setUserId(user.getId());
        userExam.setExamId(examId);
        userExam.setScore(resultDTO.getScore());
        userExam.setStatus(resultDTO.getStatus());
        userExam.setFinishedDate(LocalDateTime.now());
        userExamRepo.save(userExam);
    }

    User getUserFromToken(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        return userRepo.findUserByUsername(username);
    }

    Exam convertToExam(ExamDTO dto) {
        Exam exam = new Exam();
        if (dto.getId() != null) {
            exam = examRepo.getById(dto.getId());
        }
        exam.setExamCode(dto.getExamCode());
        exam.setTitle(dto.getTitle());
        exam.setDescription(dto.getDescription());
        exam.setPassingScore(dto.getPassingScore());
        exam.setDuration(dto.getDuration());
        // exam.setQuestions(dto.getQuestions());
        return exam;
    }

    ExamDTO convertToExamDTO(Exam exam) {
        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(exam.getId());
        examDTO.setExamCode(exam.getExamCode());
        examDTO.setTitle(exam.getTitle());
        examDTO.setDescription(exam.getDescription());
        examDTO.setPassingScore(exam.getPassingScore());
        examDTO.setDuration(exam.getDuration());
        // examDTO.setQuestions(exam.getQuestions());
        examDTO.setNumberOfQuestions(exam.getNumberOfQuestions());
        String based64ImageSrc = resolveBannerImage(exam.getBannerImage());
        examDTO.setBannerImageSource(based64ImageSrc);
        return examDTO;
    }

    ExamResultDTO convertToExamResultDTO(UserExam userExam) {
        ExamResultDTO dto = new ExamResultDTO();
        Exam exam = examRepo.getById(userExam.getExamId());
        dto.setExamId(userExam.getExamId());
        dto.setTitle(exam.getTitle());
        dto.setDescription(exam.getDescription());
        dto.setStatus(userExam.getStatus());
        dto.setScore(userExam.getScore());
        dto.setPassingScore(exam.getPassingScore());
        dto.setFinishedAt(userExam.getFinishedDate());
        String based64ImageSrc = resolveBannerImage(exam.getBannerImage());
        dto.setBannerImage(based64ImageSrc);
        return dto;
    }

    String resolveBannerImage(String imageSrc) {
        if (imageSrc == null) {
            imageSrc = pathConfig.getUploadPath() + pathConfig.getDefaultCertImage();
        }
        return ImageUtils.encodeToBased64(imageSrc);
    }
}
