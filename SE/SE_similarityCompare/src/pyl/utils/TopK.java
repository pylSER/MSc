package pyl.utils;

import java.util.ArrayList;
import java.util.List;

public class TopK {

    public List getTopK(List list,int topk){
        List res=new ArrayList();

        for (int i = 0; i < list.size(); i++) {
            if(i>=topk){
                break;
            }

            res.add(list.get(i));
        }

        return res;
    }
}
