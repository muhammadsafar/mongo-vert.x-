package id.co.iconpln.mongo.mongo_errorlog.dto;

import org.bson.Document;

public class HeLogError {

	private String meterId;
	private String readDate;
	private String requestStartDate;
	private String requestEndDate;
	private String trxId;
	private String date;
	private Integer errorCode;
	private String errorMessage;
	private Long readDateEpoch;

	public HeLogError(Document doc) {
		if (doc.getString("meterId") != null) {
			this.meterId = doc.getString("meterId");
		}
		if (doc.getString("readDate") != null) {
			this.readDate = doc.getString("readDate");
		}
		if (doc.getString("requestStartDate") != null) {
			this.requestStartDate = doc.getString("requestStartDate");
		}
		if (doc.getString("requestEndDate") != null) {
			this.requestEndDate = doc.getString("requestEndDate");
		}
		if (doc.getString("trxId") != null) {
			this.trxId = doc.getString("trxId");
		}
		if (doc.getString("date") != null) {
			this.date = doc.getString("date");
		}
		if (doc.getInteger("errorCode") != null) {
			this.errorCode = doc.getInteger("errorCode");
		}
		if (doc.getString("errorMessage") != null) {
			this.errorMessage = doc.getString("errorMessage");
		}
		if (doc.getLong("readDateEpoch") != null) {
			this.readDateEpoch = doc.getLong("readDateEpoch");
		}

	}

	public HeLogError() {
	}

	public HeLogError(String meterId, String readDate, String requestStartDate, String requestEndDate, String trxId,
			String date, Integer errorCode, String errorMessage, Long readDateEpoch) {
		super();
		this.meterId = meterId;
		this.readDate = readDate;
		this.requestStartDate = requestStartDate;
		this.requestEndDate = requestEndDate;
		this.trxId = trxId;
		this.date = date;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.readDateEpoch = readDateEpoch;
	}

	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public String getReadDate() {
		return readDate;
	}

	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}

	public String getRequestStartDate() {
		return requestStartDate;
	}

	public void setRequestStartDate(String requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public String getRequestEndDate() {
		return requestEndDate;
	}

	public void setRequestEndDate(String requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getReadDateEpoch() {
		return readDateEpoch;
	}

	public void setReadDateEpoch(Long readDateEpoch) {
		this.readDateEpoch = readDateEpoch;
	}

}
