package com.redprojects.vk.api;

import com.redprojects.utils.Log;
import com.redprojects.utils.Utils;
import com.redprojects.vk.api.exceptions.VKAPIException;
import com.redprojects.vk.api.methods.Users;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import static com.redprojects.utils.Utils.addElement;
import static com.redprojects.utils.Utils.arrayToUrlParameters;

public class VKAPI implements Cloneable {

    @SuppressWarnings({"UnusedDeclaration", "SpellCheckingInspection"})
    public enum Scope {
        notify, friends, photos, audio, video,
        docs, notes, pages, status, offers,
        questions, wall, groups, messages, email,
        notifications, stats, ads, offline, nohttps
    }

    public enum ResponseType {
        token
    }

    public static final String apiVersion = "5.14";

    private final String accessToken;
    private final int userId;
    private final int appId;
    private final String secret;
    private final Scope[] scopes;
    private final String redirectUri;
    private final ResponseType responseType;

    public VKAPI(String accessToken) {
        this(accessToken, 0);
    }

    public VKAPI(String accessToken, int userId) {
        this(accessToken, userId, -1);
    }

    public VKAPI(String accessToken, int userId, int appId) {
        this(accessToken, userId, appId, "");
    }

    public VKAPI(String accessToken, int userId, int appId, String secret) {
        this(accessToken, userId, appId, secret, new Scope[0]);
    }

    public VKAPI(String accessToken, int userId, int appId, Scope[] scopes) {
        this(accessToken, userId, appId, "", scopes, "https://oauth.vk.com/blank.html", ResponseType.token);
    }

    public VKAPI(String accessToken, int userId, int appId, String secret, Scope[] scopes) {
        this(accessToken, userId, appId, secret, scopes, "https://oauth.vk.com/blank.html", ResponseType.token);
    }

    public VKAPI(String accessToken, int userId, int appId, String secret, Scope[] scopes, String redirectUri, ResponseType responseType) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.appId = appId;
        this.secret = secret;
        this.scopes = scopes;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
    }

    public boolean validateToken() {
        try {
            getResponse("account.getCounters", new String[0][]);
        } catch (VKAPIException e) {
            return false;
        }
        return true;
    }

    public JSONObject getCurrentUser(Users.NameCase nameCase) throws VKAPIException {
        return userId > 0 ? new Users(this).get(new String[]{String.valueOf(userId)}, new Users.Fields[0], nameCase) : new JSONObject();
    }

    public int getUserId() {
        return userId;
    }

    public static JSONObject getResponseWithoutAuthorization(String method, String[][] args) throws VKAPIException {
        args = addElement(args, new String[]{"v", apiVersion});
        String url = "https://api.vk.com/method/" + method;
        try {
            String response = Utils.executePost(url, args);

            return check(new JSONObject(response));
        } catch (IOException e) {
            Log.console(null, "Не удалось выполнить запрос " + url + Arrays.deepToString(args));
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public JSONObject getResponse(String method, String[][] args) throws VKAPIException {
        return getResponseWithoutAuthorization(method, addElement(args, new String[]{"access_token", accessToken}));
    }

    public static JSONObject check(JSONObject response) throws VKAPIException {
        if (response.has("error")) {
            VKAPIException vkapiException = VKAPIException.getException(response.getJSONObject("error").getInt("error_code"));
            vkapiException.initCause(new VKAPIException(response.toString()));
            throw vkapiException;
        }
        return response;
    }

    public String getUrl(String method, String[][] args) {
        return "https://api.vk.com/method/" + method + arrayToUrlParameters(addElement(addElement(args, new String[]{"access_token", accessToken}), new String[]{"v", apiVersion}));
    }

    public String getLoginUrl() {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < scopes.length; i++) {
            buf.append(scopes[i]);
            if (i < scopes.length - 1)
                buf.append(",");
        }
        return "https://oauth.vk.com/authorize?" +
                "client_id=" + appId +
                "&scope=" + buf.toString() +
                "&redirect_uri=" + redirectUri +
                "&v=" + apiVersion +
                "&response_type=" + ResponseType.token.name();
    }

    @SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "CloneDoesntCallSuperClone"})
    public VKAPI clone() {
        return new VKAPI(accessToken, userId, appId, secret, scopes, redirectUri, responseType);
    }
}
