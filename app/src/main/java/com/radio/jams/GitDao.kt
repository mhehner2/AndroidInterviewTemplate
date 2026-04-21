package com.radio.jams

class GitDao(
    private val database: GitDatabase,
) {

    fun getById(id: Int): GitRepository? {
        return database.repositoryQueries
            .selectById(id.toLong())
            .executeAsOneOrNull()
            ?.let { GitRepository(id = it.id, full_name = it.full_name) }
    }

    fun getAllRepositories(): List<GitRepository> {
        return database.repositoryQueries
            .selectAll()
            .executeAsList()
            .map { GitRepository(id = it.id, full_name = it.full_name) }
    }

    fun insertRepositories(gitRepositories: List<GitRepository>) {
        database.transaction {
            gitRepositories.forEach { repo ->
                database.repositoryQueries.insertRepo(repo.id, repo.full_name)
            }
        }
    }

    fun deleteRepository(gitRepository: GitRepository) {
        database.repositoryQueries.deleteRepo(gitRepository.id)
    }
}
