package br.com.rec.app;

public class RequestModel {

	private long id;
    private String content;
    private String inputTwoTowns;
    private String result;
    private String error;
    private int graphOption;
    private int numMaxOfStops;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getInputTwoTowns() {
		return inputTwoTowns;
	}

	public void setInputTwoTowns(String inputTwoTowns) {
		this.inputTwoTowns = inputTwoTowns;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getGraphOption() {
		return graphOption;
	}

	public void setGraphOption(int graphOption) {
		this.graphOption = graphOption;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getNumMaxOfStops() {
		return numMaxOfStops;
	}

	public void setNumMaxOfStops(int numMaxOfStops) {
		this.numMaxOfStops = numMaxOfStops;
	}
}
