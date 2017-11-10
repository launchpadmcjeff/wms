# AWS Infrastructure Initialization

## Prerequisites
The CodeCommit repository polled by the pipeline
The repository should be added as a remote and pushed
An AWS key in the region for the instance stacks, such as single-instance-template or blue-green-template.

boot the wildfly with this:
bin/standalone.sh -b=0.0.0.0 # http://blog.arungupta.me/bind-wildfly-different-ip-address-multihomed/


```
$ aws cloudformation create-stack --profile wms  --stack-name wms-roles --template-body file://aws/service-roles-template.json --capabilities CAPABILITY_IAM
{
    "StackId": "arn:aws:cloudformation:us-east-1:737930803050:stack/wms-roles/8e4ec600-c567-11e7-9ec6-500c2866f062"
}
```


```
$ aws cloudformation create-stack --profile wms  --stack-name wms-vpc --template-body file://aws/vpc-only-template.json  
{
    "StackId": "arn:aws:cloudformation:us-east-1:737930803050:stack/wms-vpc/1590eee0-c568-11e7-b339-50a686e4bb82"
}
```

```
$ aws cloudformation create-stack --profile wms  --stack-name wms-codebuild --template-body file://aws/codebuild-template.json 
{
    "StackId": "arn:aws:cloudformation:us-east-1:737930803050:stack/wms-codebuild/2ddc9170-c568-11e7-a5e3-500c20fefad2"
}
```

```
$ aws cloudformation create-stack --profile wms  --stack-name wms-codedeploy --template-body file://aws/codedeploy-template.json 
{
    "StackId": "arn:aws:cloudformation:us-east-1:737930803050:stack/wms-codedeploy/3e468890-c568-11e7-8c15-50fae98b1835"
}
```

```
$ aws cloudformation create-stack --profile wms  --stack-name wms-pipeline --template-body file://aws/pipeline-short-template.json --parameters ParameterKey=Email,ParameterValue=launchpadmcjeff@gmail.com
{
    "StackId": "arn:aws:cloudformation:us-east-1:737930803050:stack/wms-pipeline/c2231840-c568-11e7-a875-503aca261699"
}
```



```
$ aws cloudformation delete-stack --profile wms --stack-name wms-pipeline
```

```
$ aws cloudformation delete-stack --profile wms --stack-name wms-codedeploy
```

```
$ aws cloudformation delete-stack --profile wms --stack-name wms-codebuild
```

```
$ aws s3 rm s3://wms-vpc-codepipeline-us-east-1 --recursive
delete: s3://wms-vpc-codepipeline-us-east-1/wms/Source01/gLyZfVX
delete: s3://wms-vpc-codepipeline-us-east-1/wms/Build01/HiynoGL
```

```
$ aws cloudformation delete-stack --profile wms --stack-name wms-vpc
```

```
$ aws cloudformation delete-stack --profile wms --stack-name wms-roles
```

