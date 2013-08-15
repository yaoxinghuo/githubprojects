package com.googlecode.jsonplugin;

import javax.servlet.http.HttpServletResponse;

import com.googlecode.jsonplugin.missing.TextUtils;

public class SerializationParams {
    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private final HttpServletResponse response;
    private final String encoding;
    private final boolean wrapWithComments;
    private final String serializedJSON;
    private final boolean smd;
    private final boolean gzip;
    private final boolean noCache;
    private final int statusCode;
    private final int errorCode;
    private final boolean prefix;
    private String contentType = DEFAULT_CONTENT_TYPE;
    private String wrapPrefix;
    private String wrapSuffix;

    public SerializationParams(HttpServletResponse response, String encoding, boolean wrapWithComments,
                               String serializedJSON, boolean smd, boolean gzip, boolean noCache, int statusCode,
                               int errorCode, boolean prefix, String contentType,
                               String wrapPrefix, String wrapSuffix) {
        this.response = response;
        this.encoding = encoding;
        this.wrapWithComments = wrapWithComments;
        this.serializedJSON = serializedJSON;
        this.smd = smd;
        this.gzip = gzip;
        this.noCache = noCache;
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.prefix = prefix;
        this.contentType = TextUtils.noNull(contentType, DEFAULT_CONTENT_TYPE);
        this.wrapPrefix = wrapPrefix;
        this.wrapSuffix = wrapSuffix;
    }

    public SerializationParams(HttpServletResponse response, String defaultEncoding, boolean wrapWithComments, String json, boolean b, boolean b1, boolean noCache, int i, int i1, boolean prefix, String contentType) {
        this(response, defaultEncoding, wrapWithComments, json, b, b1, noCache, i, i1, prefix, contentType, null, null);
    }

    public String getWrapSuffix() {
        return wrapSuffix;
    }

    public String getWrapPrefix() {
        return wrapPrefix;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getEncoding() {
        return encoding;
    }

    public boolean isWrapWithComments() {
        return wrapWithComments;
    }

    public String getSerializedJSON() {
        return serializedJSON;
    }

    public boolean isSmd() {
        return smd;
    }

    public boolean isGzip() {
        return gzip;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public boolean isPrefix() {
        return prefix;
    }

    public String getContentType() {
        return contentType;
    }
}
