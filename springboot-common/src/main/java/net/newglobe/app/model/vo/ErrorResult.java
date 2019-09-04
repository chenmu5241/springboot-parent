package net.newglobe.app.model.vo;

public class ErrorResult extends Result{

	private static final long serialVersionUID = 1L;
	
	String errorDetail;

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}


}
