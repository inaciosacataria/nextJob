package com.decode.nextjob.domain.data

import com.decode.nextjob.models.Job

class JobResponse (
    var page_count: Int,
    var has_next_page : Boolean,
    var jobs: List<Job>) {
}
