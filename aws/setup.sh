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


function getBooleanHssOrDie {
	while :
	do
		echo "getBooleanHss [1/0y/n/Y/N]"
		echo $1
		read junk
		if [ $junk = "y" ]
		then
			break	
		fi
	done

}

function install {
	aws cloudformation create-stack --region $1  --stack-name wms-codecommit \
		--template-body file://codecommit-template.json
	
	getBooleanHssOrDie "Say y when the wms-codecommit stack is complete and the release is pushed..."

	aws cloudformation create-stack --region $1  --stack-name wms-roles \
		--template-body file://service-roles-template.json --capabilities CAPABILITY_IAM

	aws cloudformation create-stack --region $1  --stack-name wms-vpc \
		--template-body file://vpc-${2}az-template.json

	while :
	do
		getBooleanHss "Say y when the wms-vpc stack is complete..."
		if [ $? -eq 0 ]
		then
			break
		fi
	done

	aws cloudformation create-stack --region $1  --stack-name wms-codebuild \
		--template-body file://codebuild-template.json

	aws cloudformation create-stack --region $1  --stack-name wms-codedeploy \
		--template-body file://codedeploy-template.json 


	while :
	do
		getBooleanHss "Say y when the wms-codedeploy stack is complete..."
		if [ $? -eq 0 ]
		then
			break
		fi
	done

	aws cloudformation create-stack --region $1  --stack-name wms-pipeline \
		--template-body file://pipeline-short-template.json \
		--parameters ParameterKey=Email,ParameterValue=launchpadmcjeff@gmail.com

}


function uninstall {
	aws cloudformation delete-stack --region $1 --stack-name wms-blue-green
	aws cloudformation delete-stack --region $1 --stack-name wms-pipeline
	while :
	do
		getBooleanHss "Say y when the wms-pipeline stack is deleted..."
		if [ $? -eq 0 ]
		then
			break
		fi
	done
	aws cloudformation delete-stack --region $1 --stack-name wms-codedeploy

	aws cloudformation delete-stack --region $1 --stack-name wms-codebuild

	aws s3 rm s3://wms-vpc-codepipeline-$1 --recursive

	aws cloudformation delete-stack --region $1 --stack-name wms-vpc


	aws cloudformation delete-stack --region $1 --stack-name wms-roles

}



if [ "$1" = "install" ]
then
	echo "install needs repo and key"
	install $2 $3
fi

if [ "$1" = "uninstall" ]
then
	echo "uninstalling..."
	uninstall $2
fi

echo "done $1"
