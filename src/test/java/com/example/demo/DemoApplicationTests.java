package com.example.demo;

import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	QuestionRepository questionRepository;
	@Test
	void t1() {
		System.out.println("test test hihi");
		Question q1 = new Question();
		q1.setSubject("배가영");
		q1.setContent("바부");
		q1.setCreateDate(LocalDateTime.now());

		questionRepository.save(q1);
	}

	@Test
	void t2() {
		String tmpTitle = "제목";
		List<Question> qlist = new ArrayList<>();
		for(int i = 0; i < 300; i++) {
			StringBuilder sb = new StringBuilder(tmpTitle);
			Question q1 = new Question();
			q1.setSubject(sb.append("_").append(i).toString());
			q1.setContent("냉무");
			q1.setCreateDate(LocalDateTime.now());
			qlist.add(q1);
		}

		questionRepository.saveAll(qlist);
	}
}
