package com.decode.nextjob.domain.data.network

import com.apollographql.apollo.ApolloClient

class ApoloBuilder {
    var appoloClient= ApolloClient.builder().serverUrl("https://api.graphql.jobs/").build()
}