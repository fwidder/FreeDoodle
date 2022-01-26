package com.github.fwidder.freedoodle.api.v1.approve.model;

import com.github.fwidder.freedoodle.model.Answer;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class ApproveDoodleRequest {
	private Map<LocalDate, Answer> answerMap;
}
