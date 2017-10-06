package mailClient;


import mailClient.Store;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhenya
 */
public class TestBoundary {
    public static void main(String[] args) {
        String example="multipart/mixed;    boundary=\"----==--bound.6104.web34o.yandex.ru\"";
        System.out.println("TestBoundary.main() example="+example);
        int s=Store.getNextInd(example, "boundary=");
        example=example.substring(s);
        System.out.println("TestBoundary.main() example="+example);
        s=Store.getNextInd(example, "\"");
        example=example.substring(s);
        System.out.println("TestBoundary.main() example="+example);
        int e=Store.getNextInd(example, "\"");
        example=example.substring(0,e-1);
        System.out.println("TestBoundary.main() example="+example);
        if (example.equals("----==--bound.6104.web34o.yandex.ru"))
            System.out.println("mailClient.TestBoundary.main() pass");
        else
            System.out.println("mailClient.TestBoundary.main() failure");
    }
}
