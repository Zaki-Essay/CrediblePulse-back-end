package xyz.crediblepulse.crediblepulsebackend.constants;

public final class ApiPaths {

    // context Apis
    public static final String API = "/api";

    // properties
    public static final String PUBLIC = "/public";

    // Accounts
    public static final String ACCOUNTS = "/accounts";
    public static final String USERS = "/users";
    public static final String ME = "/me";

    // emails
    public static final String EMAILS = "/emails";

    // Invitation
    public static final String MEMBERSHIPS_INVITATIONS = "/memberships_invitations";
    public static final String PATH_VARIABLE_INVITATION_ID = "/{" + PathParams.INVITATION_ID + "}";
    public static final String EMAILS_VERIFICATION = "/emails-verification";
    public static final String ROLE = "/role";
    public static final String VERIFICATIONS = "/verifications";
    public static final String MEETINGS = "/meetings";
    public static final String TEAMS = "/teams";
    public static final String SEARCH = "/search";
    public static final String CURRENT = "/current";
    public static final String SCHEDULES = "/schedules";
    public static final String STATUS = "/status";
    public static final String CALENDAR = "/calendar";
    public static final String GOOGLE_AUTH = "/google/auth";
    public static final String GOOGLE_LINK = "/google/link";
    public static final String NOTES = "/notes";

    // Path Variables
    public static final String PATH_VARIABLE_ID = "/{" + PathParams.ID + "}";

    public static final String PATH_VARIABLE_TOKEN = "/{" + PathParams.TOKEN + "}";

    // versions
    public static final String V1 = "/v1";

    public static final String PATH_VARIABLE_MEETING_ID = "/{" + PathParams.MEETING_ID + "}";

    public static final String PATH_VARIABLE_NOTE_ID = "/{" + PathParams.NOTE_ID + "}";

    // Organization

    public static final String ORGANIZATION = "/organizations";
    public static final String MEMBERS = "/members";
    public static final String HISTORY = "/history";
    public static final String UPCOMING = "/upcoming";

    // Management

    public static final String MANAGEMENT = "/management";

    public static final String ELIGIBLE_MEMBERS = "/eligible_members";

    public static final String MANAGED_MEMBERS = "/managed_members";

    public static final String MANAGER = "/manager";

    private ApiPaths() {}
}
