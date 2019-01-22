import { createStore, combineReducers } from 'redux';
import toggleModal from './reducers/modalReducer';

const rootReducer = combineReducers({
    toggleModal: toggleModal,
})

export default createStore(rootReducer)
