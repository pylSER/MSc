# valueIterationAgents.py
# -----------------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


import mdp, util

from learningAgents import ValueEstimationAgent

class ValueIterationAgent(ValueEstimationAgent):
    """
        * Please read learningAgents.py before reading this.*

        A ValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs value iteration
        for a given number of iterations using the supplied
        discount factor.
    """
    def __init__(self, mdp, discount = 0.9, iterations = 100):
        """
          Your value iteration agent should take an mdp on
          construction, run the indicated number of iterations
          and then act according to the resulting policy.

          Some useful mdp methods you will use:
              mdp.getStates()
              mdp.getPossibleActions(state)
              mdp.getTransitionStatesAndProbs(state, action)
              mdp.getReward(state, action, nextState)
              mdp.isTerminal(state)
        """
        self.mdp = mdp
        self.discount = discount
        self.iterations = iterations
        self.values = util.Counter() # A Counter is a dict with default 0


        # Write value iteration code here
        "*** YOUR CODE HERE ***"
        self.values.incrementAll(mdp.getStates(),0)

        for i in range(iterations):
            previous=self.values.copy()
            for currState in mdp.getStates():
                possibleActions=mdp.getPossibleActions(currState)

                valueOPTList=[]
                for nowAction in possibleActions:
                    nextStateAndProp=mdp.getTransitionStatesAndProbs(currState, nowAction)

                    valueOPT=0
                    #Q-value sums all possible

                    for stateAndProp in nextStateAndProp:
                        nextState=stateAndProp[0]
                        probility=stateAndProp[1]
                        reward=mdp.getReward(currState, nowAction, nextState)

                        valueOPT+=probility*(reward+previous[nextState]*self.discount)

                    valueOPTList.append(valueOPT)
                if len(valueOPTList)>0:
                    self.values[currState] = max(valueOPTList)

    def getValue(self, state):
        """
          Return the value of the state (computed in __init__).
        """
        return self.values[state]


    def computeQValueFromValues(self, state, action):
        """
          Compute the Q-value of action in state from the
          value function stored in self.values.
        """
        "*** YOUR CODE HERE ***"
        # util.raiseNotDefined()
        # print state
        # print action
        # print "-----"

        qvalue=0
        nextStateAndProp=self.mdp.getTransitionStatesAndProbs(state, action)
        for stateAndProp in nextStateAndProp:
            nextState = stateAndProp[0]
            probility = stateAndProp[1]
            reward=self.mdp.getReward(state, action, nextState)
            qvalue+=(probility*(reward+self.discount*self.values[nextState]))
        return qvalue


    def computeActionFromValues(self, state):
        """
          The policy is the best action in the given state
          according to the values currently stored in self.values.

          You may break ties any way you see fit.  Note that if
          there are no legal actions, which is the case at the
          terminal state, you should return None.
        """
        "*** YOUR CODE HERE ***"
        # util.raiseNotDefined()

        if self.mdp.isTerminal(state):
            return None

        if self.mdp.getPossibleActions(state)[0]=='exit':
            return 'exit'

        currentMaxQVal=-999
        maxAction=''

        for i in range(4):
            currAction=''
            if i==0:
                currAction = 'north'
            elif i==1:
                currAction = 'south'
            elif i==2:
                currAction = 'west'
            else:
                currAction = 'east'

            qval = self.computeQValueFromValues(state,currAction)

            if qval>currentMaxQVal:
                currentMaxQVal=qval
                maxAction=currAction
        return maxAction


    def getPolicy(self, state):
        """
                What is the best action to take in the state. Note that because
                we might want to explore, this might not coincide with getAction
                Concretely, this is given by

                policy(s) = arg_max_{a in actions} Q(s,a)

                If many actions achieve the maximal Q-value,
                it doesn't matter which is selected.
        """
        return self.computeActionFromValues(state)

    def getAction(self, state):
        """
                state: can call state.getLegalActions()
                Choose an action and return it.
        """
        "Returns the policy at the state (no exploration)."
        return self.computeActionFromValues(state)

    def getQValue(self, state, action):
        """
            Should return Q(state,action)
        """
        return self.computeQValueFromValues(state, action)
