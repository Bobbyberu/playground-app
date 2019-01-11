import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ImageUploader from 'react-images-upload';

const styles = () => ({
  root: {
    width: '100%',
  },
});

class Upload extends React.Component {
  constructor(props) {
    super(props);
    this.state = { pictures: [] };
    this.onDrop = this.onDrop.bind(this);
  }

  onDrop(picture) {
    this.setState({
      pictures: this.state.pictures.concat(picture),
    });
  }

  render() {
    const { classes } = this.props;
    return (
      <div>
        <ImageUploader
          className={classes.root}
          withIcon
          buttonText="Choose images"
          onChange={this.onDrop}
          imgExtension={['.jpg', '.gif', '.png', '.gif']}
          maxFileSize={5242880}
        />
      </div>
    );
  }
}

Upload.propTypes = {
  classes: PropTypes.object,
};

export default withStyles(styles)(Upload);
