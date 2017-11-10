#!/bin/sh

function getBooleanHss {
	echo "getBooleanHss [1/0y/n/Y/N]"
	echo $1
	read junk
	if [ $junk = "y" ]
	then
		return 0
	else
		return 1
	fi
}


function install {
	aws cloudformation create-stack --profile wms  --stack-name wms-roles \
		--template-body file://service-roles-template.json --capabilities CAPABILITY_IAM

	aws cloudformation create-stack --profile wms  --stack-name wms-vpc \
		--template-body file://vpc-only-template.json

	while :
	do
		getBooleanHss "Say y when the wms-vpc stack is complete..."
		if [ $? -eq 0 ]
		then
			break
		fi
	done

	aws cloudformation create-stack --profile wms  --stack-name wms-codebuild \
		--template-body file://codebuild-template.json

	aws cloudformation create-stack --profile wms  --stack-name wms-codedeploy \
		--template-body file://codedeploy-template.json 


	while :
	do
		getBooleanHss "Say y when the wms-codedeploy stack is complete..."
		if [ $? -eq 0 ]
		then
			break
		fi
	done

	aws cloudformation create-stack --profile wms  --stack-name wms-pipeline \
		--template-body file://pipeline-short-template.json \
		--parameters ParameterKey=Email,ParameterValue=launchpadmcjeff@gmail.com

}


function uninstall {
	aws cloudformation delete-stack --profile wms --stack-name wms-blue-green
	aws cloudformation delete-stack --profile wms --stack-name wms-pipeline
	aws cloudformation delete-stack --profile wms --stack-name wms-codedeploy

	aws cloudformation delete-stack --profile wms --stack-name wms-codebuild

	aws s3 rm s3://wms-vpc-codepipeline-us-east-1 --recursive

	aws cloudformation delete-stack --profile wms --stack-name wms-vpc


	aws cloudformation delete-stack --profile wms --stack-name wms-roles

}

install
#uninstall

