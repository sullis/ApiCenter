package com.tngtech.apicenter.backend.connector.rest.controller

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.tngtech.apicenter.backend.connector.rest.dto.SpecificationMetaData
import com.tngtech.apicenter.backend.connector.rest.dto.VersionDto
import com.tngtech.apicenter.backend.connector.rest.mapper.VersionDtoMapper
import com.tngtech.apicenter.backend.domain.entity.ApiLanguage
import com.tngtech.apicenter.backend.domain.entity.Version
import com.tngtech.apicenter.backend.domain.service.VersionPersistenceService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class VersionControllerUnitTest {

    private val versionPersistenceService: VersionPersistenceService = mock()

    private val versionDtoMapper: VersionDtoMapper = mock()

    private val versionController = VersionController(versionPersistenceService, versionDtoMapper)

    private val specificationId = "7de07d27-eedb-4290-881a-6a402a81dd0f"

    private val metadata = SpecificationMetaData("Swagger Petstore", "1.0.0", "Description", ApiLanguage.OPENAPI, null)

    private val version = Version("Content", metadata)

    private val versionDto = VersionDto("Content", metadata)

    @Test
    fun findOne_shouldReturnVersionDto() {
        given(versionPersistenceService.findOne(specificationId, "1.0")).willReturn(version)
        given(versionDtoMapper.fromDomain(version)).willReturn(versionDto)

        assertThat(versionController.findVersion(specificationId, "1.0")).isEqualTo(versionDto)
    }

    @Test
    fun delete_shouldDeleteVersion() {
        versionController.deleteVersion(specificationId, "1.0")

        verify(versionPersistenceService).delete(specificationId, "1.0")
    }

}