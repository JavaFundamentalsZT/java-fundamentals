package org.zeroturnaround.jf2012.concurrency.forkjoin;
/*
* Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*   - Redistributions of source code must retain the above copyright
*     notice, this list of conditions and the following disclaimer.
*
*   - Redistributions in binary form must reproduce the above copyright
*     notice, this list of conditions and the following disclaimer in the
*     documentation and/or other materials provided with the distribution.
*
*   - Neither the name of Oracle or the names of its
*     contributors may be used to endorse or promote products derived
*     from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
* IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
* PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
* EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
* LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import java.util.concurrent.*;

import javax.imageio.*;
import javax.swing.*;

/**
 * ForkBlur implements a simple horizontal image blur. It averages pixels in the
 * source array and writes them to a destination array. The sThreshold value
 * determines whether the blurring will be performed directly or split into
 * two tasks.
 *
 * This is not the recommended way to blur images; it is only intended to
 * illustrate the use of the Fork/Join framework.
 */
public class ForkBlur extends RecursiveAction {
  private int[] mSource;
  private int mStart;
  private int mLength;
  private int[] mDestination;
  
  private int mBlurWidth = 15; // Processing window size, should be odd.
  
  public ForkBlur(int[] src, int start, int length, int[] dst) {
    mSource = src;
    mStart = start;
    mLength = length;
    mDestination = dst;
  }
  
  // Average pixels from source, write results into destination.
  protected void computeDirectly() {
    int sidePixels = (mBlurWidth - 1) / 2;
    for (int index = mStart; index < mStart + mLength; index++) {
      // Calculate average.
      float rt = 0, gt = 0, bt = 0;
      for (int mi = -sidePixels; mi <= sidePixels; mi++) {
        int mindex = Math.min(Math.max(mi + index, 0), mSource.length - 1);
        int pixel = mSource[mindex];
        rt += (float)((pixel & 0x00ff0000) >> 16) / mBlurWidth;
        gt += (float)((pixel & 0x0000ff00) >>  8) / mBlurWidth;
        bt += (float)((pixel & 0x000000ff) >>  0) / mBlurWidth;
      }
      
      // Re-assemble destination pixel.
      int dpixel = (0xff000000     ) |
                   (((int)rt) << 16) |
                   (((int)gt) <<  8) |
                   (((int)bt) <<  0);
      mDestination[index] = dpixel;
    }
  }
  
  protected static int sThreshold = 10000;
  
  protected void compute() {
    if (mLength < sThreshold) {
      computeDirectly();
      return;
    }
    
    int split = mLength / 2;
    
    invokeAll(new ForkBlur(mSource, mStart,         split,           mDestination),
              new ForkBlur(mSource, mStart + split, mLength - split, mDestination));
  }
  
  // Plumbing follows.
  
  public static void main(String[] args) throws Exception {
    String filename = "waves.jpg";
    File file = new File(filename);
    BufferedImage image = ImageIO.read(file);
    
    new ImageFrame("ForkBlur - original", image);
    
    BufferedImage blurredImage = blur(image);
    
    new ImageFrame("ForkBlur - processed", blurredImage);
  }

  public static BufferedImage blur(BufferedImage srcImage) {
    int w = srcImage.getWidth();
    int h = srcImage.getHeight();
    
    int[] src = srcImage.getRGB(0, 0, w, h, null, 0, w);
    int[] dst = new int[src.length];

    System.out.println("Array size is " + src.length);
    System.out.println("Threshold is " + sThreshold);
    
    int processors = Runtime.getRuntime().availableProcessors();
    System.out.println(Integer.toString(processors) + " processor" +
        (processors != 1 ? "s are " : " is ") + 
        "available");
    
    ForkBlur fb = new ForkBlur(src, 0, src.length, dst);
    
    ForkJoinPool pool = new ForkJoinPool();
    
    long startTime = System.currentTimeMillis();
    pool.invoke(fb);
    long endTime = System.currentTimeMillis();
    
    System.out.println("Image blur took " + (endTime - startTime) + " milliseconds.");
    
    BufferedImage dstImage =
        new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    dstImage.setRGB(0, 0, w, h, dst, 0, w);
  
    return dstImage;
  }
}

class ImageFrame extends JFrame {
  public ImageFrame(String title, BufferedImage image) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(image.getWidth(), image.getHeight());
    add(new ImagePanel(image));
    setLocationByPlatform(true);
    setVisible(true);
  }    
}

class ImagePanel extends JPanel {
    BufferedImage mImage;

    public ImagePanel(BufferedImage image) {
        mImage = image;
    }

    protected void paintComponent(Graphics g) {
        int x = (getWidth() - mImage.getWidth())/2;
        int y = (getHeight() - mImage.getHeight())/2;
        g.drawImage(mImage, x, y, this);
    }
}
