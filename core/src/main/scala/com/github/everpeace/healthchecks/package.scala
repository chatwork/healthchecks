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

/*
 * The original source code were written by https://github.com/timeoutdigital.
 * Original source code are distributed with MIT License.
 * Please see: https://github.com/timeoutdigital/akka-http-healthchecks
 * The codes are modified from original one by Shingo Omura.
 */

package com.github.everpeace

import cats.data.ValidatedNel
import cats.syntax.validated._
import com.github.everpeace.healthchecks.HealthCheck.Severity

import scala.concurrent.Future
import scala.util.Try

package object healthchecks {
  type HealthCheckResult = ValidatedNel[String, Unit]

  def healthy: HealthCheckResult = ().validNel[String]

  def unhealthy(msg: String): HealthCheckResult = msg.invalidNel[Unit]

  def healthCheck(
      name: String,
      severity: Severity = Severity.Fatal
  )(c: => HealthCheckResult): HealthCheck =
    new HealthCheck(name, Future.fromTry(Try(c)), severity)

  def asyncHealthCheck(
      name: String,
      severity: Severity = Severity.Fatal
  )(c: => Future[HealthCheckResult]): HealthCheck =
    new HealthCheck(name, c, severity)
}
