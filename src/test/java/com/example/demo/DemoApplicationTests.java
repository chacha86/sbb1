package com.example.demo;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerRepository;
import com.example.demo.answer.AnswerService;
import com.example.demo.question.DataNotFoundException;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;
import com.example.demo.question.QuestionService;
import com.example.demo.user.SiteUser;
import com.example.demo.user.UserRepository;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AnswerRepository answerRepository;
	@Test
	void t1() {
		Optional<Question> qo = questionRepository.findById(11112);
		if(qo.isPresent()) {
			System.out.println("hihi");
		} else {
			throw new DataNotFoundException("hhhahahahaha");
		}
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

	@Autowired
	private QuestionService questionService;
	private AnswerService answerService;
	@Test
	void t3() {
//		Question q1 = questionService.getQuestion(answerForm.getQuestionId());
//		answerService.create(answerForm.getContent(), q1);
	}

	@Autowired
	PersonRepository personRepository;
	@Autowired
	PersonDetailRepository personDetailRepository;
	@Test
	void t4() {
		PersonDetail pd = new PersonDetail();
		Person p1 = new Person();
		pd.setAge(20);
		pd.setPerson(p1);
		p1.setName("chacha");
		p1.setDetail(pd);
		personDetailRepository.save(pd);
		personRepository.save(p1);
	}

	@Test
	void t5_CrateData() {
		Question q1 = new Question();
		Question q2 = new Question();

		SiteUser s1 = new SiteUser();
		SiteUser s2 = new SiteUser();

		q1.setSubject("hihi");
		q1.setContent("byby");
		q1.setCreateDate(LocalDateTime.now());

		q2.setSubject("hihi2");
		q2.setContent("byby2");
		q2.setCreateDate(LocalDateTime.now());

		s1.setLoginId("chacha");
		s1.setPasswd("ckxowls1!");
		s1.setCrateDate(LocalDateTime.now());

		s2.setLoginId("chocho");
		s2.setPasswd("chocho");
		s2.setCrateDate(LocalDateTime.now());

		userRepository.save(s1);
		userRepository.save(s2);

		Set<SiteUser> voterList1 = new HashSet<>();

		voterList1.add(s1);
		voterList1.add(s2);

		Set<SiteUser> voterList2 = new HashSet<>();

		voterList2.add(s1);

		q1.setVoter(voterList1);
		q2.setVoter(voterList2);
		questionRepository.save(q1);
		questionRepository.save(q2);


		Answer answer1 = new Answer();
		Answer answer2 = new Answer();


		answer1.setCreateDate(LocalDateTime.now());
		answer1.setContent("comment1");
		answer1.setVoter(voterList1);
		answer1.setQuestion(q1);
		answer2.setCreateDate(LocalDateTime.now());
		answer2.setContent("comment2");
		answer2.setVoter(voterList2);
		answer2.setQuestion(q1);

		answerRepository.save(answer1);
		answerRepository.save(answer2);

		List<Answer> answerList = new ArrayList<>();
		answerList.add(answer1);
		answerList.add(answer2);

		q1.setAnswerList(answerList);
		questionRepository.save(q1);
	}

}

interface PersonRepository extends JpaRepository<Person, Long> {

}
interface PersonDetailRepository extends JpaRepository<PersonDetail, Long> {

}

@Entity
class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
	private PersonDetail detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonDetail getDetail() {
		return detail;
	}

	public void setDetail(PersonDetail detail) {
		this.detail = detail;
	}
}

@Entity
class PersonDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@MapsId
	@JoinColumn(name="id")
	private Person person;
	private Integer age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}