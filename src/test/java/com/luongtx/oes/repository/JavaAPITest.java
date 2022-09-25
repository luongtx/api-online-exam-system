package com.luongtx.oes.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JavaAPITest {

	@Test
	public void test() {
		log.info("test");
		assertEquals(2, 1 + 1);
	}

	@Test
	public void test2() {
		Integer x = null;
		Integer a = Optional.ofNullable(x).orElse(0);
		assertEquals(a.intValue(), 0);
	}

	@Test
	public void test3() {
		List<String> list = Arrays.asList("a", "b");
		// List<String> list = null;
		List<String> list2 = Optional.ofNullable(list)
				.map(Collection::stream)
				.orElseGet(Stream::empty)
				.map(e -> e + "i")
				.collect(Collectors.toList());
		for (String a : list2) {
			System.out.println(a);
		}
	}
}
