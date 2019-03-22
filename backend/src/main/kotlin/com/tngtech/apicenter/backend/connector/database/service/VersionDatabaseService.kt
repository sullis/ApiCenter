package com.tngtech.apicenter.backend.connector.database.service

import com.tngtech.apicenter.backend.connector.database.entity.VersionId
import com.tngtech.apicenter.backend.connector.database.mapper.VersionEntityMapper
import com.tngtech.apicenter.backend.connector.database.repository.VersionRepository
import com.tngtech.apicenter.backend.domain.entity.Version
import com.tngtech.apicenter.backend.domain.service.VersionPersistenceService
import org.springframework.stereotype.Service

@Service
class VersionDatabaseService constructor(
    private val versionRepository: VersionRepository,
    private val versionEntityMapper: VersionEntityMapper
) : VersionPersistenceService {

    override fun findOne(specificationId: String, versionTitle: String): Version? {
        return versionRepository.findById(VersionId(specificationId, versionTitle)).orElse(null)?.let { version -> versionEntityMapper.toDomain(version) }
    }


    override fun delete(specificationId: String, versionTitle: String) {
        versionRepository.deleteById(VersionId(specificationId, versionTitle))
    }
}