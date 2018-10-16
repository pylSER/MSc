# search.py
# ---------
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


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]


# dfsResult=util.PriorityQueue()
dfsResult=[]
isDFSDone=False



def dfsApproach(problem,startState,visited,prevActions):
    global dfsResult
    global isDFSDone

    if isDFSDone:
        return

    if problem.isGoalState(startState):
        for act in prevActions:
            dfsResult.append(act)
        # dfsResult.push(dfsActions,len(dfsActions))
        # print dfsActions
        isDFSDone=True

        return

    visited.append(startState)

    for successorState, action, stepCost in problem.getSuccessors(startState):
        if successorState not in visited:
            newVisit=[]
            for v in visited:
                newVisit.append(v)
            newVisit.append(successorState)

            newActions=[]
            for a in prevActions:
                newActions.append(a)
            newActions.append(action)
            dfsApproach(problem,successorState,newVisit,newActions)

    return


def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:



    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())
    """
    "*** YOUR CODE HERE ***"
    global dfsResult

    visited=[]

    prevActions=[]

    dfsApproach(problem,problem.getStartState(),visited,prevActions)


    if len(dfsResult)>0:
        return dfsResult
    else:
        return False
    # util.raiseNotDefined()

def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""

    # initialize the search tree using the initial state of problem
    queue = util.Queue()
    queue.push((problem.getStartState(), []))
    visited = []

    while not queue.isEmpty():
        # choose a leaf node for expansion
        state, actions = queue.pop()

        # print("Start:")
        # print(state)
        #
        # print("Start's successors:")
        # print(problem.getSuccessors(state))


        # if the node contains a goal state then return the corresponding solution
        if problem.isGoalState(state):
            return actions

        # expand the node and add the resulting nodes to the search tree
        if state not in visited:
            visited.append(state)
            for successor, action, stepCost in problem.getSuccessors(state):
                if successor not in visited:
                    queue.push((successor, actions + [action]))
    return False

def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    util.raiseNotDefined()

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def getAStarActions(endNode,fatherNodeDict):
    actions=[]

    fatherNode=fatherNodeDict[endNode]

    while fatherNode!=0:
        fatherPosition=(fatherNode[0],fatherNode[1])
        endPosition=(endNode[0],endNode[1])

        actions.append(getAction(fatherPosition,endPosition))
        endNode=fatherNode
        fatherNode=fatherNodeDict[endNode]
    actions.reverse()
    return actions


def getAction(start,end):
    from game import Directions

    if start[0]>end[0]:
        return Directions.WEST
    elif start[0]<end[0]:
        return Directions.EAST
    else:
        if start[1]>end[1]:
            return Directions.SOUTH
        else:
            return Directions.NORTH



def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    fatherNodeDict={}

    costDict={}

    pathCostDict={}

    openSet=util.PriorityQueue()
    pathCost=0
    closeSet=[]



    startPosition=(problem.getStartState()[0],problem.getStartState()[1])

    startCost=pathCost+heuristic(startPosition,problem,info=problem.getStartState())

    openSet.push(problem.getStartState(),startCost)
    fatherNodeDict[problem.getStartState()]=0
    costDict[problem.getStartState()]=heuristic(startPosition,problem,info=problem.getStartState())
    pathCostDict[problem.getStartState()]=0


    while not openSet.isEmpty():

        shortestNode=openSet.pop()

        closeSet.append(shortestNode)

        if problem.isGoalState(shortestNode):
            return getAStarActions(shortestNode,fatherNodeDict)


        pathCost=pathCostDict[shortestNode]+1
        for successor, action, stepCost in problem.getSuccessors(shortestNode):
            successorPosition=(successor[0],successor[1])
            successorCost=pathCost+heuristic(successorPosition,problem,info=successor)

            if successor not in closeSet:
                if costDict.__contains__(successor):
                    # if in the open list
                    orgCost=costDict.get(successor)
                    if successorCost<orgCost:
                        openSet.update(successor, successorCost)
                        fatherNodeDict[successor] = shortestNode
                        costDict[successor] = heuristic(successorPosition,problem,info=successor)
                        pathCostDict[successor]=pathCost
                else:
                    #if not in the open list
                    openSet.push(successor,successorCost)
                    fatherNodeDict[successor] = shortestNode
                    costDict[successor] = heuristic(successorPosition,problem,info=successor)
                    pathCostDict[successor] = pathCost
            else:
                if costDict.__contains__(successor):
                    #if in the close set
                    orgCost = costDict.get(successor)
                    if successorCost<orgCost:
                        openSet.update(successor, successorCost)
                        fatherNodeDict[successor] = shortestNode
                        costDict[successor] = heuristic(successorPosition,problem,info=successor)
                        pathCostDict[successor] = pathCost

    return False


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
