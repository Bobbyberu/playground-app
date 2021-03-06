const initialState = {
    openAddPlayground: false,
    openAddComment: false,
    openReportPlayground: false,
    openReportComment: false,
    reportedComment: null,
    openPasswordDialog: false,
    openMailDialog: false,
};

function toggleModal(state = initialState, action) {
    let nextState;
    switch (action.type) {
        case 'TOGGLE_ADD_PLAYGROUND':
            // nextState take value of action received (true or false)
            // handle modal to add playground in home page
            nextState = {
                // get all previous states to avoid undefined on states that will remain unchanged
                ...state,
                openAddPlayground: action.value
            }
            return nextState || state;

        case 'TOGGLE_REPORT_PLAYGROUND':
            // handle modal to report a playground in playground details page
            nextState = {
                ...state,
                openReportPlayground: action.value,
            }
            return nextState || state;

        case 'TOGGLE_ADD_COMMENT':
            // handle comment modal in playground details page
            nextState = {
                ...state,
                openAddComment: action.value,
            }
            return nextState || state;
        case 'TOGGLE_REPORT_COMMENT':
            // handle modal to report a comment in playground details page
            nextState = {
                ...state,
                openReportComment: action.value.open,
                reportedComment: action.value.comment,
            }
            return nextState || state;
        
        case 'TOGGLE_PASSWORD_DIALOG':
            // handle modal to change password
            nextState = {
                ...state,
                openPasswordDialog: action.value,
            }
            return nextState || state;

        case 'TOGGLE_MAIL_DIALOG':
            // handle modal to change mail
            nextState = {
                ...state,
                openMailDialog: action.value,
            }
            return nextState || state;

        default:
            return state;
    }
}

export default toggleModal