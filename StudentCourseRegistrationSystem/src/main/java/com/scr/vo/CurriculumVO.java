/**
 * 
 */
package com.scr.vo;

/**
 * @author pavanibaradi
 *
 */
public class CurriculumVO {
	private int curriculumId;
	private String topicName;
	/**
	 * @param curriculumId
	 * @param topicName
	 */
	public CurriculumVO(int curriculumId, String topicName) {
		super();
		this.curriculumId = curriculumId;
		this.topicName = topicName;
	}
	/**
	 * @param topicName
	 */
	public CurriculumVO(String topicName) {
		super();
		this.topicName = topicName;
	}
	/**
	 * @return the curriculumId
	 */
	public int getCurriculumId() {
		return curriculumId;
	}
	/**
	 * @param curriculumId the curriculumId to set
	 */
	public void setCurriculumId(int curriculumId) {
		this.curriculumId = curriculumId;
	}
	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}
	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

}
