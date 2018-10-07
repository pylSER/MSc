__author__ = 'peiyulin'
import cv2
import numpy as np

patternKp=''
patternDes=''


# We generate a mask using Homography Estimation to screen incorrect matches again
def getHomographyMask(matchedPairs,imgKp):
    if len(matchedPairs)>10:
        src_pts = np.float32([patternKp[m.queryIdx].pt for m in matchedPairs ]).reshape(-1,1,2)
        dst_pts = np.float32([imgKp[m.trainIdx].pt for m in matchedPairs]).reshape(-1,1,2)
        M, mask = cv2.findHomography(src_pts, dst_pts, cv2.RANSAC,5.0)
        matchesMask = mask.ravel().tolist()
        return matchesMask
    else:
        return None


# use knnMatches to find the best 2 match
# screen out incorrect matches among these matches
def myScreenOut(matchedPairs,imgKp):
    rest = []
    arr4Homography=[]

    # if two descriptors are smiliar, it's a bad match
    #smiliar means the distances of two descriptor to the target descriptor are similar
    DISTANCE_RATIO=0.7
    for m,n in matchedPairs:
        if m.distance < DISTANCE_RATIO*n.distance:
            rest.append([m])
            arr4Homography.append(m)

    mask=getHomographyMask(arr4Homography,imgKp)

    afterrest = []

    # return rest

    if mask is not None:
        for i in range(len(mask)):
            if mask[i]==1:
                afterrest.append(rest[i])
        return afterrest
    else:
        return rest




def getSift(pattern):
    sift = cv2.xfeatures2d.SIFT_create()
    return sift.detectAndCompute(pattern,None)


#for each image, only use sift once
def perpaerPattern(pattern):
    global patternKp
    global patternDes
    patternKp,patternDes=getSift(pattern)

def mysiftalignment(Image1,Image2):
    imgKp,imgDes=getSift(Image2)

    bf = cv2.BFMatcher()

    #we use best k match to screen some wrong matches first
    matches = bf.knnMatch(patternDes,imgDes, k=2)

    rest=myScreenOut(matches,imgKp)

    return rest,imgKp


def startMatch(patternImg,Image,outputName):
    global patternKp

    matchedPairs,imgKp=mysiftalignment(patternImg,Image)

    result=cv2.drawMatchesKnn(patternImg,patternKp,Image,imgKp,matchedPairs,None,flags=2)

    cv2.imwrite(outputName, result)

    return