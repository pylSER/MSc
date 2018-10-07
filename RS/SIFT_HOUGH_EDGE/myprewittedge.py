__author__ = 'peiyulin'
import numpy as np

originalImage=''

def getMaskResult(mask,pixelCube):
    return abs(np.sum(mask*pixelCube))


def doMaskProcess(mask,processedImage):
    global originalImage

    [a,b]=processedImage.shape

    result=np.zeros(originalImage.shape)

    for i in range(1,a-1):
        for j in range(1,b-1):
            pixelCube=processedImage[i-1:i+2,j-1:j+2]
            value=getMaskResult(mask,pixelCube)
            result[i-1,j-1]=value

    return result



def adaptThershold(responseMap):
    global originalImage

    minT=np.min(responseMap)
    maxT=np.max(responseMap)
    meanT=(minT+maxT)/2


    for count in range(10):

        thresholdImg=np.where(responseMap>=meanT,1,0)

        biggerIntensitySum=0
        biggerNums=0

        smallerIntensitySum=0
        smallerNums=0

        [a,b]=originalImage.shape

        for i in range(a):
            for j in range(b):
                if thresholdImg[i,j]==1:
                    biggerIntensitySum+=originalImage[i,j]
                    biggerNums+=1
                else:
                    smallerIntensitySum+=originalImage[i,j]
                    smallerNums+=1


        meanBiggerIntensity=biggerIntensitySum/biggerNums
        meanSmallerIntensity=smallerIntensitySum/smallerNums

        meanT=(meanBiggerIntensity+meanSmallerIntensity)/2

    return meanT


#surround image with 0s
def prepareImg4Prewitt(Image):
    processedImage=np.zeros((Image.shape[0]+2,Image.shape[1]+2))

    [a,b]=processedImage.shape

    processedImage[1:a-1,1:b-1]=Image

    # processedImage[0,1:b-1]=Image[0]
    # processedImage[a-1,1:b-1]=processedImage[a-2,1:b-1]
    #
    # processedImage[:,0]=processedImage[:,1]
    # processedImage[:,b-1]=processedImage[:,b-2]

    return processedImage


def setOriginImage(image):
    global originalImage
    originalImage=image


# for 'all' we use |gx|+|gy|+|g+45|+|g-45| to get the gradient
def myprewittedge(Image,direction,T=None):
    processedImage=prepareImg4Prewitt(Image)

    if direction=='all':
        return myprewittedge(Image,'horizontal',T)+myprewittedge(Image,'vertical',T)+myprewittedge(Image,'pos45',T)+myprewittedge(Image,'neg45',T)
        # return myprewittedge(Image,'horizontal',T)+myprewittedge(Image,'vertical',T)

    mask=''
    if direction=='horizontal':
        mask=np.array([[-1,-1,-1],[0,0,0],[1,1,1]])
    elif direction=='vertical':
        mask=np.array([[-1,0,1],[-1,0,1],[-1,0,1]])
    elif direction=='pos45':
        mask=np.array([[0,-1,-1],[1,0,-1],[1,1,0]])
    elif direction=='neg45':
        mask=np.array([[-1,-1,0],[-1,0,1],[0,1,1]])

    result=doMaskProcess(mask,processedImage)


    if T is None:
        T=adaptThershold(result)

    newImage=np.where(result>=T,255,0)
    newImage=newImage.astype(np.uint8)
    return newImage