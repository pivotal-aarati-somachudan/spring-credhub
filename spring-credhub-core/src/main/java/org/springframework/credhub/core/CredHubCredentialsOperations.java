/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.credhub.core;

import org.springframework.credhub.support.CredentialDetails;
import org.springframework.credhub.support.CredentialName;
import org.springframework.credhub.support.CredentialPath;
import org.springframework.credhub.support.CredentialRequest;
import org.springframework.credhub.support.CredentialSummary;
import org.springframework.credhub.support.ParametersRequest;

import java.util.List;

/**
 * Specifies the interactions with CredHub to save, generate, retrieve,
 * and delete credentials.
 *
 * @author Scott Frederick
 */
public interface CredHubCredentialsOperations {
	/**
	 * Write a new credential to CredHub, or overwrite an existing credential with a new
	 * value.
	 *
	 * @param credentialRequest the credential to write to CredHub; must not be {@literal null}
	 * @param <T> the credential implementation type
	 * @return the details of the written credential
	 */
	<T> CredentialDetails<T> write(final CredentialRequest<T> credentialRequest);

	/**
	 * Generate a new credential in CredHub, or overwrite an existing credential with a new
	 * generated value.
	 *
	 * @param parametersRequest the parameters of the new credential to generate in CredHub;
	 *                                must not be {@literal null}
	 * @param <T> the credential implementation type
	 * @param <P> the credential parameter implementation type
	 * @return the details of the generated credential
	 */
	<T, P> CredentialDetails<T> generate(final ParametersRequest<P> parametersRequest);

	/**
	 * Regenerate a credential in CredHub. Only credentials that were previously generated can be
	 * re-generated.
	 *
	 * @param name the name of the credential; must not be {@literal null}
	 * @param <T> the credential implementation type
	 * @return the details of the regenerated credential
	 */
	<T> CredentialDetails<T> regenerate(final CredentialName name);

	/**
	 * Retrieve a credential using its ID, as returned in a write request.
	 *
	 * @param id the ID of the credential; must not be {@literal null}
	 * @param credentialType the type of the credential to be retrieved; must not be {@literal null}
	 * @param <T> the credential implementation type
	 * @return the details of the retrieved credential
	 */
	<T> CredentialDetails<T> getById(final String id, final Class<T> credentialType);

	/**
	 * Retrieve a credential using its name, as passed to a write request.
	 * Only the current credential value will be returned.
	 *
	 * @param name the name of the credential; must not be {@literal null}
	 * @param credentialType the type of credential expected to be returned
	 * @param <T> the credential implementation type
	 * @return the details of the retrieved credential
	 */
	<T> CredentialDetails<T> getByName(final CredentialName name, final Class<T> credentialType);

	/**
	 * Retrieve a credential using its name, as passed to a write request.
	 * A collection of all stored values for the named credential will be returned,
	 * including historical values.
	 *
	 * @param name the name of the credential; must not be {@literal null}
	 * @param credentialType the type of credential expected to be returned
	 * @param <T> the credential implementation type
	 * @return the details of the retrieved credential, including history
	 */
	<T> List<CredentialDetails<T>> getByNameWithHistory(final CredentialName name, final Class<T> credentialType);

	/**
	 * Retrieve a credential using its name, as passed to a write request.
	 * A collection of stored values for the named credential will be returned,
	 * with the specified number of historical values.
	 *
	 * @param name the name of the credential; must not be {@literal null}
	 * @param versions the number of historical versions to retrieve
	 * @param credentialType the type of credential expected to be returned
	 * @param <T> the credential implementation type
	 * @return the details of the retrieved credential, including history
	 */
	<T> List<CredentialDetails<T>> getByNameWithHistory(final CredentialName name, int versions,
														final Class<T> credentialType);

	/**
	 * Find a credential using a full or partial name.
	 *
	 * @param name the name of the credential; must not be {@literal null}
	 * @return a summary of the credential search results
	 */
	List<CredentialSummary> findByName(final CredentialName name);

	/**
	 * Find a credential using a path.
	 *
	 * @param path the path to the credential; must not be {@literal null}
	 * @return a summary of the credential search results
	 */
	List<CredentialSummary> findByPath(final String path);

	/**
	 * Retrieve a collection of all paths that contain credentials.
	 *
	 * @return a collection of paths
	 */
	List<CredentialPath> getAllPaths();

	/**
	 * Delete a credential by its full name.
	 *
	 * @param name the name of the credential; must not be {@literal null}
	 */
	void deleteByName(final CredentialName name);
}
