package com.workpoint.mwallet.shared.responses;

import java.util.ArrayList;
import java.util.List;

import com.workpoint.mwallet.shared.model.GradeCountDTO;


public class GetGradeCountRequestResult extends BaseResponse {

	private List<GradeCountDTO> gradeCount;

	@SuppressWarnings("unused")
	private GetGradeCountRequestResult() {
		// For serialization only
		gradeCount = new ArrayList<GradeCountDTO>();
	}

	public GetGradeCountRequestResult(List<GradeCountDTO> gradeCounts) {
		this.gradeCount = gradeCounts;
	}

	public List<GradeCountDTO> getGradeCount() {
		return gradeCount;
	}

	public void setGradeCount(List<GradeCountDTO> gradeCount) {
		this.gradeCount = gradeCount;
	}

}
