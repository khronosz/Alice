import React from 'react';
import { Toast } from 'react-bootstrap';

class MyToast extends React.Component {

    state = {
        show: false
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (!this.state.show && this.props.message) {
            this.setState({ show: true })
            if (this.props.clear) setTimeout(() => this.hide(), 2000)
        }
    }

    hide = () => {
        this.props.reset()
        this.setState({ show: false })
    }

    render() {

        const toastCss = {
            position: 'fixed',
            top: '10px',
            right: '10px',
            zIndex: '1',
            boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)'
        }

        const toastClass = this.props.type === "success" ? "border-success bg-success" : "border-danger bg-danger"
        const headerClass = this.props.type === "success" ? "bg-success" : "bg-danger"

        return (
            <>
                {this.state.show &&
                    <div style={toastCss}>
                        <Toast className={"text-white border" + toastClass}>
                            <Toast.Header className={"text-white " + headerClass} closeButton={false}>
                                <strong className="mr-auto">Success</strong>
                            </Toast.Header>
                            <Toast.Body>{this.props.message}</Toast.Body>
                        </Toast>
                    </div>
                }
            </>
        );
    }
}

export default MyToast;