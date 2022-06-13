import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.httpGet

private val baseUrl = "https://api.github.com"

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
  @JsonProperty("followers")
  val followers: Long,
  @JsonProperty("followers_url")
  val followersUrl: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Follower(
  @JsonProperty("url")
  val url: String
)

data class Response(
  val status: Int,
  val count: Long,
)

private val mapper = jacksonObjectMapper()

fun main(args: Array<String>) {
  val userName = args[0]

  val userJson = ("$baseUrl/users/$userName").httpGet().response().second.data
  val user = mapper.readValue(userJson, User::class.java)

  val followersResponse = user.followersUrl.httpGet().response().second.data
  val followers = mapper.readValue(followersResponse, object: TypeReference<List<Follower>>(){})

  val count = followers
    .subList(0, 3).map {
      val followingUserJson = it.url.httpGet().response().second.data
      mapper.readValue(followingUserJson, User::class.java)
    }
    .sumOf {
      it.followers
    }

  val response = Response(status = 200, count = count)
  val responseJson = mapper.writeValueAsString(response)
  println(responseJson)
}
