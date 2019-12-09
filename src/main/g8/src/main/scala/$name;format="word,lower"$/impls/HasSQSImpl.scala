package $organization$.$name;format="word,lower"$
package impls

import environments.AppEnv
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import us.oyanglul.zhuyu.effects.{HasSQS, SQSConfig}

trait HasSQSImpl extends HasSQS with HasConfig {
  lazy val sqsConfig =
    SQSConfig(config.sqsConfig.sqsQueueUrl, config.sqsConfig.longPollSeconds)
  lazy val sqsClient =
    if (config.appEnv == AppEnv.Local)
      AmazonSQSClientBuilder
        .standard()
        .withCredentials(
          new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(config.sqsConfig.accessId,
                                    config.sqsConfig.accessKey)))
        .withEndpointConfiguration(
          new EndpointConfiguration(config.sqsConfig.host,
                                    config.sqsConfig.region))
        .build()
    else
      AmazonSQSClientBuilder
        .standard()
        .build
}
