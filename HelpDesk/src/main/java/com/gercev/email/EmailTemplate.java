package com.gercev.email;

public enum EmailTemplate {
    NEW_TICKET_FOR_APPROVAL("html/NewTicketApproval.html","New ticket for approval"),
    TICKET_WAS_APPROVED("html/TicketApproved.html","Ticket was approved"),
    TICKET_WAS_DECLINED("html/TicketDeclined.html","Ticket was declined"),
    TICKET_WAS_CANCELLED_MANAGER("html/TicketCancelledManager.html","Ticket was cancelled"),
    TICKET_WAS_CANCELLED_ENGINEER("html/TicketCancelledEngineer.html","Ticket was cancelled"),
    TICKET_WAS_DONE("html/TicketDone.html","Ticket was done"),
    TICKET_WAS_PROVIDED("html/TicketProvided.html","Ticket was provided");

    private final String templateName;
    private final String subject;

    EmailTemplate(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getSubject() {
        return subject;
    }
}
