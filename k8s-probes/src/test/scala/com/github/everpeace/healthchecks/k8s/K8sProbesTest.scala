/*
 * Copyright (c) 2021 Chatwork Co., Ltd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.everpeace.healthchecks.k8s

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, StatusCodes}
import com.github.everpeace.healthchecks._
import com.github.everpeace.healthchecks.k8s._
import org.scalatest.funspec.AnyFunSpec

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class K8sProbesTest extends AnyFunSpec {

  describe("K8sProbes") {
    it("should start successfully and return correct response") {
      implicit val system = ActorSystem()
      implicit val ec     = system.dispatcher

      try {
        bindAndHandleProbes(
          readinessProbe(healthCheck("readiness_check")(healthy)),
          livenessProbe(asyncHealthCheck("liveness_check")(Future(healthy)))
        )

        def requestToLivenessProbe =
          Http().singleRequest(HttpRequest(uri = "http://localhost:8086/live"))

        def requestToReadinessProbe =
          Http().singleRequest(HttpRequest(uri = "http://localhost:8086/ready"))

        val livenessResponse = Await.result(requestToLivenessProbe, 10 seconds)
        val redinessResponse = Await.result(requestToReadinessProbe, 10 seconds)

        assert(livenessResponse.status === StatusCodes.OK)
        assert(redinessResponse.status === StatusCodes.OK)
      } finally {
        system.terminate()
      }
    }
  }
}
