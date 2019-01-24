import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ImageUploader from 'react-images-upload';
import { connect } from 'react-redux';
import Snackbar from '@material-ui/core/Snackbar';
import SnackbarContentWrapper from '../../../common-components/snackbar/SnackbarContentWrapper';
import './Upload.css';

const maxSize = 5242880;

const styles = () => ({
  root: {
    width: '100%',
  },
});

class Upload extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: false,
    }
  }

  // Envoi de l'image dans le reducer si sa taille le permet
  handleSelectedFile = event => {
    let data = new FormData();
    if (event[0]){
      data.append('file', event[0], event[0].name);
      const action = { type: 'SET_IMG', value: data};
      this.props.dispatch(action);
    }else {
      this.setState({
        error: true,
      })
    }
  }

  handleSnackbarClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    this.setState({
      error: false
    })
  }

  render() {
    const { classes } = this.props;
    return (
      <div>
        <ImageUploader
          className={classes.root}
          name='file'
          withIcon
          buttonText="Choisir une image"
          onChange={this.handleSelectedFile}
          imgExtension={['.jpg', '.png']}
          maxFileSize={maxSize}
          singleImage={true}
        />
        <Snackbar
          anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'left',
          }}
          open={this.state.error}
          autoHideDuration={6000}
          onClose={this.handleSnackbarClose}
        >
          <SnackbarContentWrapper
            onClose={this.handleSnackbarClose}
            variant="error"
            message={'L\'image sélectionnée est trop importante'}
          />
        </Snackbar>
      </div>
    );
  }
}

Upload.propTypes = {
  classes: PropTypes.object,
};

// mapping du state global dans les props du composant Upload
const mapStateToProps = (state) => {
  return {
    image: state.addPlayground.image,
    lat: state.addPlayground.latitude,
    lon: state.addPlayground.longitude,
  }
}

// mapStateToProps pour abonner le composant aux changements du store Redux
export default connect(mapStateToProps)(withStyles(styles)(Upload));
