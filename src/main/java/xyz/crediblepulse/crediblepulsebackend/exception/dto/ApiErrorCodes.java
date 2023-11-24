package xyz.crediblepulse.crediblepulsebackend.exception.dto;

public enum ApiErrorCodes implements KeyValueError {

    // User
    USER_NOT_VALID_ERROR(2601, "user.not.valid"),
    USER_CREATION_ERROR(2602, "user.creation.error"),
    USER_NOT_FOUND(2603, "user.not.found"),
    EMAIL_ALREADY_VERIFIED(2610, "email.already.verified"),
    USER_UPDATE_ERROR(2612, "user.update.error"),
    USER_NOT_ACTIVE(2613, "user.not.active"),
    USER_WITH_EMAIL_ALREADY_MEMBER(2671, "user.already.member"),

    // Organization
    ORGANISATION_ALREADY_EXIST_ERROR(2604, "exception.organisation.name.exist"),
    ORGANIZATION_NOT_FOUND(2604, "exception.organisation.not.found"),
    NOT_MEMBER_OF_ORGANIZATION(2603, "user.not.member"),

    // User Assignment
    NOT_MANAGER(2603, "user.not.manager"),

    NOT_USER_MANAGER(2603, "not.manager.of.user"),
    NOT_ELIGIBLE_ASSIGNMENT(2603, "not.eligible.assignment"),

    // Invitations
    INVITATION_NOT_FOUND(2604, "invitation.not.found"),

    INVITATION_IS_EXPIRED(2604, "invitation.already.expired"),

    INVITATION_STATUS_EXCEPTION(2604, "invitation.status.exception"),

    INVITATION_INVALID_RECIPIENT(2604, "invitation.invalid.recipient"),

    INVITATION_ALREADY_SENT(2670, "invitation.already.sent"),

    // Organization Membership
    MEMBERSHIP_HIRING_DATE_INVALID(2632, "membership.hiring.date.invalid"),

    MEMBERSHIP_TERMINATION_DATE_INVALID(2633, "membership.termination.date.invalid"),

    // validation
    REQUIRED_FIELD(2606, "exception.required.field"),

    EMAIL_INVALID(2606, "exception.email.invalid"),

    PHONE_NUMBER_INVALID(2607, "phone.number.invalid"),

    PROVIDER_INVALID(2608, "provider.invalid"),

    EMAIL_SENDING_EXCEPTION(2611, "email.sending.exception"),

    TIMEZONE_INVALID(2614, "timezone.invalid"),

    STATUS_INVALID(2614, "status.invalid"),

    ACTION_INVALID(2614, "action.invalid"),
    ID_INVALID(2614, "id.invalid"),

    // Teams
    TEAM_MEMBERSHIP_NOT_FOUND(2609, "team.membership.not.found"),

    ROLE_INVALID(2615, "role.invalid"),

    // Membership
    TEAM_MANAGER_NOT_FOUND(2612, "team.manager.not.found"),

    SUSPENDED_ACCESS_TO_TEAM(2613, "team.membership.suspended"),

    TARGET_TEAM_NOT_FOUND(2614, "team.membership.target.not.found"),

    // Meeting
    MEETING_TITLE_INVALID(2615, "meeting.invalid.title"),
    START_TIME_INVALID(2616, "invalid.start_time"),
    END_TIME_INVALID(2617, "invalid.end_time"),
    MEETING_NOT_FOUND(2618, "meeting.not.found"),

    MEETING_DATE_CANNOT_BE_CHANGED(2619, "meeting.date.cannot.be.changed"),

    MEETING_PARTICIPANT_CANNOT_BE_CHANGED(2620, "meeting.participant.cannot.be.changed"),

    USER_NOT_IN_TEAM(2621, "meeting.user.not.in.team"),

    MEETING_CONTAINS_PARTICIPANT(2622, "meeting.contains.participant"),

    MEETING_ID_MISSING(2623, "meeting.missing.subordinateId"),

    MEETING_MEMBER_ID_MISSING(2624, "meeting.missing.member_id"),

    GOOGLE_AUTHORIZATION_INVALID_ERROR(2628, "google.authorization.invalid"),

    ACCOUNT_ALREADY_LINKED(2629, "google.account.already_linked"),

    CANNOT_CREATE_MEETING_CALENDAR_EVENT(2630, "google.cannot.create.calendar.event"),

    AUTHZ_CODE_REQUIRED(2631, "google.cannot.create.calendar.event"),

    GOOGLE_CALENDAR_NOT_LINKED(2632, "google.calendar.not.linked"),

    GOOGLE_CALENDAR_EXCEPTION(2633, "google.calendar.exception"),

    MEETING_TYPE_INVALID(2634, "meeting.invalid.type"),

    MEETING_FREQUENCY_INVALID(2635, "meeting.frequency.type"),

    PARTICIPANTS_INVALID_FOR_MEETING_TYPE(2636, "participants.invalid.for.meeting.type"),

    MEETING_PARTICIPANTS_INVALID(2636, "meeting.participants.invalid"),

    CANNOT_UPDATE_MEETING_AFTER_CANCELLATION(2637, "meeting.cannot.be.updated.after.cancel"),

    CANNOT_CANCEL_MEETING(2638, "meeting.cannot.cancel"),

    CANNOT_UPDATE_COMPLETED_MEETING_STATUS(2639, "cannot.update.completed.meeting.status"),

    ONGOING_MEETING_ALREADY_EXISTS(2639, "ongoing.meeting.already.exists"),

    INVALID_TIME_FRAME(2639, "invalid.timeframe"),

    CANNOT_UPDATE_MEETING_OCCURRENCE(2640, "cannot.update.meeting.occurrence"),

    INVALID_OPTION(2641, "invalid.option"),

    // Meeting Note
    NOTE_NOT_FOUND(2639, "meeting.note.not.found"),

    NOTE_TYPE_INVALID(2640, "meeting.note.type.invalid"),

    NO_EVENT_ASSOCIATED_WITH_MEETING(2639, "no.event.associated.to.meeting");

    private final Integer code;
    private final String msgKey;

    ApiErrorCodes(Integer code, String msgKey) {
        this.code = code;
        this.msgKey = msgKey;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsgKey() {
        return msgKey;
    }
}
