import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.httpGet

private val baseUrl = "https://api.github.com"

@JsonIgnoreProperties(ignoreUnknown = true)
data class User (
  @JsonProperty("followers")
  val followers: Long,
  @JsonProperty("followers_url")
  val followersUrl: String,
)

fun main(args: Array<String>) {
  val userName = args[0]
  val mapper = jacksonObjectMapper()

  val json = """
    {
      "login": "acdlite",
      "id": 3624098,
      "node_id": "MDQ6VXNlcjM2MjQwOTg=",
      "avatar_url": "https://avatars.githubusercontent.com/u/3624098?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/acdlite",
      "html_url": "https://github.com/acdlite",
      "followers_url": "https://api.github.com/users/acdlite/followers",
      "following_url": "https://api.github.com/users/acdlite/following{/other_user}",
      "gists_url": "https://api.github.com/users/acdlite/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/acdlite/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/acdlite/subscriptions",
      "organizations_url": "https://api.github.com/users/acdlite/orgs",
      "repos_url": "https://api.github.com/users/acdlite/repos",
      "events_url": "https://api.github.com/users/acdlite/events{/privacy}",
      "received_events_url": "https://api.github.com/users/acdlite/received_events",
      "type": "User",
      "site_admin": false,
      "name": "Andrew Clark",
      "company": "@facebook",
      "blog": "",
      "location": null,
      "email": null,
      "hireable": null,
      "bio": "React core at Facebook. Hi!",
      "twitter_username": "acdlite",
      "public_repos": 71,
      "public_gists": 20,
      "followers": 9858,
      "following": 0,
      "created_at": "2013-02-18T08:08:56Z",
      "updated_at": "2022-02-21T05:55:21Z"
    }
  """.trimIndent()

//  val json = ("$baseUrl/users/$userName").httpGet().response().second.data
  val user = mapper.readValue(json, User::class.java)
  println(user)

}

