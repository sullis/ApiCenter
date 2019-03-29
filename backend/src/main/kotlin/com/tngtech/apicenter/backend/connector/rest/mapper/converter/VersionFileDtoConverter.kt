package com.tngtech.apicenter.backend.connector.rest.mapper.converter

import com.tngtech.apicenter.backend.connector.rest.dto.VersionFileDto
import com.tngtech.apicenter.backend.connector.rest.dto.VersionMetaData
import com.tngtech.apicenter.backend.connector.rest.service.SpecificationDataService
import com.tngtech.apicenter.backend.connector.rest.service.SpecificationFileService
import com.tngtech.apicenter.backend.domain.entity.ApiLanguage
import com.tngtech.apicenter.backend.domain.entity.ServiceId
import com.tngtech.apicenter.backend.domain.entity.Specification
import com.tngtech.apicenter.backend.domain.entity.Version
import com.tngtech.apicenter.backend.domain.exceptions.MismatchedSpecificationIdException
import ma.glasnost.orika.CustomConverter
import ma.glasnost.orika.MappingContext
import ma.glasnost.orika.metadata.Type
import org.springframework.stereotype.Component
import java.util.*

@Component
class VersionFileDtoConverter constructor(
    private val specificationFileService: SpecificationFileService,
    private val specificationDataService: SpecificationDataService
) :
    CustomConverter<VersionFileDto, Version>() {

    override fun convert(
        versionFileDto: VersionFileDto,
        destinationType: Type<out Version>?,
        mappingContext: MappingContext?
    ): Version {
        val fileContent = getLocalOrRemoteFileContent(versionFileDto)

        val parsedFileContent = if (versionFileDto.metaData != null) fileContent
                                else specificationDataService.parseFileContent(fileContent)

        val idFromUpload = specificationDataService.extractId(parsedFileContent)
        val idFromPath = versionFileDto.id
        val serviceId = getConsistentServiceId(idFromUpload, idFromPath)

        val metadata = versionFileDto.metaData ?: makeSpecificationMetaData(parsedFileContent, serviceId)

        return Version(parsedFileContent, metadata)
    }

    private fun getLocalOrRemoteFileContent(versionFileDto: VersionFileDto): String {
        return if (versionFileDto.fileUrl != null && versionFileDto.fileUrl != "") {
            specificationFileService.retrieveFile(versionFileDto.fileUrl)
        } else {
            versionFileDto.fileContent ?: ""
        }
    }

    private fun getConsistentServiceId(idFromUpload: String?, idFromPath: String?): ServiceId {
        if (idFromUpload != null && idFromPath != null && idFromUpload != idFromPath) {
            throw MismatchedSpecificationIdException(idFromUpload, idFromPath)
        }
        return ServiceId(idFromUpload ?: idFromPath ?: UUID.randomUUID().toString())
    }

    private fun makeSpecificationMetaData(fileContent: String, serviceId: ServiceId): VersionMetaData {
        return VersionMetaData(
            serviceId,
            specificationDataService.extractTitle(fileContent),
            specificationDataService.extractVersion(fileContent),
            specificationDataService.extractDescription(fileContent),
            ApiLanguage.OPENAPI,
            null
        )
    }
}