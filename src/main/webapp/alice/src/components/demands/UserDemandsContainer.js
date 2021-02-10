import React from 'react';
import UserDemands from "./UserDemands";
import UserService from "../../services/UserService";
import DemandService from "../../services/DemandService";

class UserDemandsContainer extends React.Component {

    state = {
        content: "",
        userId: this.props.match.params.id,
        user: {},
        demands: [],
        error: undefined,
        message: undefined,
        messageType: undefined
    }

    componentDidMount() {
        this.findUserById(this.state.userId)
        this.findAllDemands()
    }

    findUserById = (id) => {
        UserService.findById(id, (response, error) => {
            if (response) {
                this.setState({user: response})
            } else {
                this.setState({ error: error })
            }
        })
    }

    findAllDemands = () => {
        DemandService.findAllByUser(this.state.userId, (response, error) => {
            if (response) {
                this.setState({demands: response})
            } else {
                this.setState({ error: error })
            }
        })
    }

    goBack = () => {
        this.props.history.goBack()
    }

    onTableChange = (type, {data, cellEdit: { rowId, dataField, newValue }}) => {
       const result = data.map((row) => {
           if (row.projectName === rowId) {
               const demand = { ...row };
               demand[dataField] = newValue;
               DemandService.update(demand.projectId, demand.id, demand, (response, error) => {
                   if (!error) {
                       this.setState({ error: undefined, message: "Demand Updated Successfully!", messageType: "success" });
                   } else {
                       this.setState({ error: error })
                   }
               })
               return demand;
           }
           return row;
       });
       this.setState(() => ({
           demands: result
       }));
    }

    resetMessage = () => this.setState({ message: undefined, messageType: undefined })

    render() {
        return(
            <UserDemands data={{
                ...this.state,
                goBack: this.goBack,
                onTableChange: this.onTableChange,
                resetMessage: this.resetMessage
            }} />
        )
    }
}

export default UserDemandsContainer;