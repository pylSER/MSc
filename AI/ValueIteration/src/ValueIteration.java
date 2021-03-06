public class ValueIteration {

    public static int ACTION_CD=1;
    public static int ACTION_STOCK=2;

    public static double DISCOUNT_FACTOR=1;

    private static double INIT_MONEY=0;


    public void doIteration(State initState){

        INIT_MONEY=initState.getCurrentMoney();


        System.out.println(getV(initState));

    }




    private boolean isEnd(State state){
        if(state.getYear()>=3){
            return true;
        }else {
            return false;
        }
    }

    private double getV(State currentState){
        //only two actions
        if(isEnd(currentState)){
            return 0;
        }else{
            double cdQ=getQ(currentState,ACTION_CD);
            double stockQ=getQ(currentState,ACTION_STOCK);

            System.out.print("for state "+currentState);
            if(cdQ>=stockQ){
                System.out.println(" we go to cd"+" V:"+cdQ);
                return cdQ;
            }else {
                System.out.println(" we go to stock  V:"+stockQ);
                return stockQ;
            }
        }
    }


    private double getQ(State currentState, int action){
        // only to actions
        if(action==ACTION_CD){
            State nextState=new State(currentState.getYear()+1,1.1*currentState.getCurrentMoney());
            double reward=getReward(currentState,ACTION_CD,nextState);

            return (reward+DISCOUNT_FACTOR*getV(nextState));

        }else{  // STOCK
            //two possiblities
            State nextState1=new State(currentState.getYear()+1,1.3*currentState.getCurrentMoney());
            double reward1=getReward(currentState,ACTION_STOCK,nextState1);

            double q1=0.7*(reward1+DISCOUNT_FACTOR*getV(nextState1));


            State nextState2=new State(currentState.getYear()+1,0.9*currentState.getCurrentMoney());
            double reward2=getReward(currentState,ACTION_STOCK,nextState2);

            double q2=0.3*(reward2+DISCOUNT_FACTOR*getV(nextState2));


            return q1+q2;
        }
    }


    private double getReward(State currentState,int action,State nextState){
        double basicProfit=nextState.getCurrentMoney()-INIT_MONEY;
        double termProfit=nextState.getCurrentMoney()-currentState.getCurrentMoney();

        if(basicProfit>0){
            return basicProfit;
        }else {
            return -10+basicProfit;
        }

    }


    public static void main(String[] args) {
        ValueIteration valueIteration=new ValueIteration();

        State initState=new State(0,1);

        valueIteration.doIteration(initState);
    }


}
