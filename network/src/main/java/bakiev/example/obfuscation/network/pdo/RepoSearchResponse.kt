package bakiev.example.obfuscation.network.pdo

import androidx.annotation.Keep

@Keep
data class RepoSearchResponse(
    val items: List<Repo>
)
