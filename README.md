# Session Context Library - Java Library for HTTP Request Header Validation

## Introduction

The Session Context Library is a powerful Java library designed to streamline the validation of crucial HTTP request headers. By utilizing Maven as its build tool, this library seamlessly integrates into any Java-based service or library by adding it as a dependency. It plays a pivotal role in ensuring the integrity and security of your microservices, focusing on the validation of the following headers: Correlation-Id, Application-Name, Component-Name, Authorization, and Session-Context.

### Key Features

1. **Correlation-Id:** Validates the Correlation-Id header, which serves as a unique identifier for requests, enabling seamless tracking of requests across multiple microservices.

2. **Application-Name and Component-Name:** Custom headers, Application-Name, and Component-Name, can be validated to uniquely identify the source of a service call. These headers are especially essential for Foundation Layer Services (ULS).

3. **Authorization Header:** Validates the Authorization header, which typically contains access tokens generated by an identity provider (IDAM). This library ensures the header contains valid tokens and checks for token expiration.

4. **Session-Context Header:** The Session-Context header is designed to hold user or application-specific details. The library validates the presence and content of this header.

## Header Validation Details

### Authorization Header Validation

The library performs validation on the Authorization header in the following steps:

1. **Null or Empty Token Check:** If the token is missing or in an invalid format, an exception is thrown with the message "Invalid or missing token."

2. **Token Expiration Check:** After decoding the token, the library checks if the token has expired. If it has, an exception is thrown with the message "Expired authorization token."

### Session-Context Header Validation

Session-Context headers are JWT tokens generated by the user service and contain user/application-specific details. The validation process is as follows:

1. **Null or Empty Check:** The library validates whether the Session-Context header is missing, empty, or null, and throws an exception with the message "Invalid or missing session context" if any of these conditions are met.

### Validation of Both Headers

The library matches the user/application details in the Session-Context and Authorization headers. If they do not match, an exception is thrown, indicating that the user is not authorized to make the request.

### Validation in Different Microservices

#### Avuri Gateway Microservice

1. **Case 1:** User-type authorization token with a Session-Context present. Both headers are decoded and checked to determine if they belong to the same user/application. If not, an exception is thrown with an HTTP status code of 403.

2. **Case 2:** User-type authorization token with an empty Session-Context. An exception is thrown with an HTTP status code of 400.

3. **Case 3:** Application-type authorization token with an empty Session-Context. This case is allowed by setting the `isSessionContextRequired` configuration flag to `false` in the Avuri gateway microservice configuration file.

4. **Case 4:** Application-type authorization token with a Session-Context. Both JWT tokens are decoded and matched; if they don't belong to the same user, an exception is thrown with an HTTP status code of 403.

#### User Manager Microservice

The User Manager Microservice is unique in that it generates Session-Context headers and supports the onboarding of new users. In certain scenarios, the validation of Session-Context and Authorization headers is skipped by setting the `isAuthorizationValidationRequired` and `isSessionContextValidationRequired` flags to `false`. However, the necessary validation code resides in the Session Context Library.

1. **Case 1:** User-type authorization token with an empty Session-Context. The library checks if the requested user email matches the decoded payload of the authorization token. If not, an exception is thrown with an HTTP status code of 403.

2. **Case 2:** User-type authorization token with a Session-Context. The library compares the decoded payload of both JWT tokens; if they belong to the same user, the validation proceeds; otherwise, an exception is thrown with an HTTP status code of 403.

3. **Case 3:** Application-type authorization token with an empty Session-Context. The library checks if the requested user email matches the decoded payload of the authorization token. If not, an exception is thrown with an HTTP status code of 403.

4. **Case 4:** Application-type authorization token with a Session-Context. Both JWT tokens are decoded and compared; if they don't belong to the same user, an exception is thrown with an HTTP status code of 403.

#### Other Microservices

For all other microservices, validation occurs in the following manner:

1. **Case 1:** User-type authorization token with a Session-Context. Both tokens are decoded and compared to determine if they belong to the same user. If not, an exception is thrown with an HTTP status of 403.

2. **Case 2:** User-type authorization token with a missing Session-Context. An exception is thrown with an HTTP status code of 400.

3. **Case 3:** Application-type authorization token with a Session-Context. The library compares the decoded payload of both JWT tokens and throws an exception with an HTTP status code of 403 if they don't belong to the same application.

4. **Case 4:** Application-type authorization token with a missing Session-Context. An exception is thrown as 'invalid or missing session-context' with an HTTP status code of 400.

### Special Cases

Validation of Session-Context headers and authorization for the Config Service and Tenant Manager is skipped by setting both the `isAuthorizationValidationRequired` and `isSessionContextValidationRequired` flags to `false`.

