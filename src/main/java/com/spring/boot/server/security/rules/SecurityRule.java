package com.spring.boot.server.security.rules;

import com.spring.boot.server.model.Student;

import javax.servlet.http.HttpServletRequest;

public interface SecurityRule {
    String IS_ANONYMOUS = "isAnonymous";
    String IS_AUTHENTICATED = "isAuthenticated";
    String DENY_ALL = "denyAll";

    SecurityRuleResult check(HttpServletRequest request, Object details);

    SecurityRuleResult check(HttpServletRequest request, Object details, Student student);
}
