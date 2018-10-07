__author__ = 'peiyulin'
import numpy as np
from skimage import transform


def trim4edges(targetImage):
    [a,b]=targetImage.shape
    return targetImage[1:a-1,1:b-1]


def getPointPairDistance(pointPair):
    xDif=pointPair[0][0]-pointPair[1][0]
    yDif=pointPair[0][1]-pointPair[1][1]

    temp=xDif*xDif+yDif*yDif

    res=np.sqrt(temp)

    return res


def mylineextraction(f,lineWidth=10):
    trimedImgae=trim4edges(f)
    lines=transform.probabilistic_hough_line(trimedImgae,line_gap=lineWidth)
    # cvlines = cv2.HoughLinesP(trimedImgae,1,np.pi/180,100,minLineLength = 40,maxLineGap=lineWidth)
    # print(cvlines)

    # lines[0]=((0,501),(0,502))
    # lines[0][0]=(0,501)
    # lines[0][0][1]=501
    distances=[getPointPairDistance(pointPair) for pointPair in lines]

    maxIndex=distances.index(max(distances))
    print("{0} {1} {2}".format("The length of the longest line is",max(distances),"pixels"))


    return [lines[maxIndex][0],lines[maxIndex][1]]
    # return ((291, 453), (299, 19))