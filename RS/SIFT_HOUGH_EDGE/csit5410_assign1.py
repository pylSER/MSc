__author__ = 'peiyulin'
import sys
import numpy as np
import cv2

import myprewittedge as prewitt
import mylineextraction as lineextract
import mysiftalignment as mysift


originalImage='';


def markTheLine(targetImage,bp,ep):
    colorImage=cv2.cvtColor(targetImage,cv2.COLOR_GRAY2RGB)
    cv2.line(colorImage, bp, ep, (255,0,0), thickness=4)

    #draw an 'x'
    cv2.line(colorImage,(bp[0]-4,bp[1]-4),(bp[0]+4,bp[1]+4), (0,0,255), thickness=3)
    cv2.line(colorImage,(bp[0]-4,bp[1]+4),(bp[0]+4,bp[1]-4),(0,0,255), thickness=3)

    #draw an 'x'
    cv2.line(colorImage,(ep[0]-4,ep[1]-4),(ep[0]+4,ep[1]+4), (0,0,255), thickness=3)
    cv2.line(colorImage,(ep[0]-4,ep[1]+4),(ep[0]+4,ep[1]-4), (0,0,255), thickness=3)
    return colorImage


def readAndSave(FILENAME):
    global originalImage
    originalImage= cv2.imread(FILENAME, cv2.IMREAD_GRAYSCALE)
    cv2.imwrite("01original.jpg", originalImage)
    return

def csit5410_assign1(FILENAME):
    global originalImage

    #Task1 start
    print("Task1 started...")
    readAndSave(FILENAME)
    print("Task1 finished.")
    #Task1 end


    prewitt.setOriginImage(originalImage)

    #Task2 start
    print("Task2 started...")
    maxIntensity=np.max(originalImage)
    g=prewitt.myprewittedge(originalImage,'all',maxIntensity*0.2)
    cv2.imwrite("02binary1.jpg", g)
    print("Task2 finished.")
    #Task2 end

    #Task3 start
    print("Task3 started...")
    g1=prewitt.myprewittedge(originalImage,'all')
    cv2.imwrite("03binary2.jpg", g1)
    print("Task3 finished.")
    #Task3 end


    #Task4 start
    print("Task4 started...")
    lineGapWidth=10
    # #we define a line has the width of 10px
    # #if the the width is 1px,there will be lots of fragmented short lines
    [bp,ep]=lineextract.mylineextraction(g1,lineGapWidth)
    g4=markTheLine(originalImage,bp,ep)
    cv2.imwrite("04longestline.jpg", g4)
    print("Task4 finished.")
    #Task4 end

    #Task5 start
    print("Task5 started...")
    qrcode=cv2.imread("QR-Code.png", cv2.IMREAD_GRAYSCALE)
    image1=cv2.imread("image1.png", cv2.IMREAD_GRAYSCALE)
    image2=cv2.imread("image2.png", cv2.IMREAD_GRAYSCALE)
    image3=cv2.imread("image3.png", cv2.IMREAD_GRAYSCALE)

    mysift.perpaerPattern(qrcode)

    mysift.startMatch(qrcode,image1,"05QR_img1.png")
    mysift.startMatch(qrcode,image2,"06QR_img2.png")
    mysift.startMatch(qrcode,image3,"07QR_img3.png")
    print("Task5 finished.")
    #Task5 end

if __name__ == '__main__':
    csit5410_assign1(sys.argv[1])