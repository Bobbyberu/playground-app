const initialState = {
    openAddPlayground: false,
    openAddComment: false,
    openReportPlayground: false,
    openReportComment: false,
    reportedComment: null
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
<<<<<<< HEAD
            // Return nextState si sa valeur n'est pas à undefined sinon return state (principe d'immuable)
            return nextState || state
=======
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
>>>>>>> develop
        default:
            return state;
    }
}

export default toggleModal