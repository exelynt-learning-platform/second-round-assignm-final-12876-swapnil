package com.multigenesys.ecommerce_service.dto;

public class PaymentResponse {

    private String approvalUrl;
    private String status;

    public PaymentResponse() {
    }

    public PaymentResponse(String approvalUrl, String status) {
        this.approvalUrl = approvalUrl;
        this.status = status;
    }

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}