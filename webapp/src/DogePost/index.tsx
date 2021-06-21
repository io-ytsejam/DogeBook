import {
  Avatar,
  Box,
  Card,
  Collapse,
  Container,
  Divider,
  ListItemAvatar,
  Menu,
  MenuItem,
  Tooltip
} from "@material-ui/core";
import Button from "@material-ui/core/Button/Button";
import Typography from "@material-ui/core/Typography";
import React, {useState} from "react";
import GradeIconOutlined from '@material-ui/icons/GradeOutlined';
import IconButton from "@material-ui/core/IconButton";
import DeleteIcon from "@material-ui/icons/Delete";
import EditIcon from '@material-ui/icons/Edit';
import NewReleasesIcon from '@material-ui/icons/NewReleasesOutlined';
import SentimentDissatisfiedIcon from '@material-ui/icons/SentimentDissatisfied';
import SentimentVerySatisfiedOutlinedIcon from '@material-ui/icons/SentimentVerySatisfiedOutlined';
import TimelineOppositeContent from "@material-ui/lab/TimelineOppositeContent/TimelineOppositeContent";
import TimelineSeparator from "@material-ui/lab/TimelineSeparator/TimelineSeparator";
import {TimelineConnector, TimelineContent, TimelineDot, TimelineItem} from "@material-ui/lab";
import FaceIcon from "@material-ui/icons/Face";
import Snackbar from "@material-ui/core/Snackbar/Snackbar";
import Alert from "../Alert";
import Comments from "../Comments";
import {getFontSizeForContent} from "../utils/getFontSizeForContent";

interface DogePostProps {
  dogePost: DogePost
  doge: Doge
  reloadDogePosts: () => void
  editDogePost: (data: DogePostEditingMode) => void
}

export default function DogePost ({ dogePost, doge: signedInDoge, reloadDogePosts, editDogePost }: DogePostProps) {
  const { content, doge, creationTime, reactions, comments, dogePostId, edited } = dogePost
  const { firstname, lastname, username } = doge
  const [mouseHovering, setMouseHovering] = useState(false)
  const [success, setSuccess] = useState(false)
  const [showComments, setShowComments] = useState(false)

  const [errorMessage, setErrorMessage] = useState('')
  const [reactionPickerAnchorEl, setReactionPickerAnchorEl] = useState<null | HTMLElement>(null);

  const openReactionPicker = (event: React.MouseEvent<HTMLButtonElement>) => {
    setReactionPickerAnchorEl(event.currentTarget);
  };

  const closeReactionPicker = () => {
    setReactionPickerAnchorEl(null);
  };

  const [reactionsListAnchorEl, setReactionsListAnchorEl] = useState<null | HTMLElement>(null);

  const openReactionsList = (event: React.MouseEvent<HTMLButtonElement>) => {
    if (!reactions.length) return
    setReactionsListAnchorEl(event.currentTarget);
  };

  const closeReactionsList = () => {
    setReactionsListAnchorEl(null);
  };

  return <TimelineItem>
    <TimelineOppositeContent style={{ marginRight: 0, marginLeft: '-30%', flex: '0' }}>
      <Typography variant="body2" color="textSecondary">
        <Tooltip title='Creation time'>
          <span>{creationTime}</span>
        </Tooltip>
      </Typography>
    </TimelineOppositeContent>
    <TimelineSeparator>
      <TimelineDot style={{ padding: 0, margin: 0 }}>
        <Tooltip title={`${firstname} ${lastname}`}>
          <Avatar style={{ height: '2rem', width: '2rem' }}>
            {`${firstname[0]}${lastname[0]}`}
          </Avatar>
        </Tooltip>
      </TimelineDot>
      <TimelineConnector />
    </TimelineSeparator>
    <TimelineContent style={{ padding: '1rem 0.5rem 0 1.25rem' }}>
      <Card
        onMouseEnter={() => setMouseHovering(true)}
        onMouseLeave={() => setMouseHovering(false)}
      >
        <Container>
          <Typography style={{ fontSize: '.75rem' }} color='textPrimary'>
            <Box display='flex'>
              <Box display='flex' width='50%' justifyContent='flex-start'>
                <Tooltip title={`@${username}`}>
                  <span>{firstname} {lastname}</span>
                </Tooltip>
              </Box>
              <Box style={{ color: 'darkgrey' }} display='flex' width='50%' justifyContent='flex-end'>
                {edited && mouseHovering ? 'This doge post was edited' : ''}
              </Box>
            </Box>
          </Typography>
          <Divider />
          <Typography style={{ fontSize: getFontSizeForContent(content) }}>
            {content}
          </Typography>
          <Divider />
          <div style={{ width: '100%' }}>
            <Box display='flex' justifyContent='space-between'>
              <Box width='50%' display='flex' justifyContent='flex-start'>
                <Tooltip title={doesSignedInDogeReacted() ? "Undo reaction" : 'Add reaction'}>
                  <IconButton
                    size='small'
                    aria-controls="simple-menu"
                    aria-haspopup="true"
                    onClick={doesSignedInDogeReacted() ? deleteReaction : openReactionPicker}
                    color='primary'
                  >
                    {doesSignedInDogeReacted() ? getIconForReactionType(reactions.find(({ doge }) => doge.username === signedInDoge.username)?.type) : <GradeIconOutlined />}
                  </IconButton>
                </Tooltip>
                <Tooltip title='Show who reacted'>
                  <Button
                    variant='text'
                    onClick={openReactionsList}
                  >
                    {`${reactions.length} ${reactions.length === 1 ? ' reaction' : ' reactions'}`}
                  </Button>
                </Tooltip>
              </Box>
              <Box display='flex' width='50%' justifyContent='flex-end'>
                {mouseHovering && username === signedInDoge.username ? <>
                  <Tooltip title='Edit doge post'>
                    <IconButton
                      size='small'
                      onClick={() => editDogePost({ dogePostId, currentContent: content })}
                    >
                      <EditIcon />
                    </IconButton>
                  </Tooltip>
                  <Tooltip title='Delete doge post'>
                    <IconButton
                      onClick={deleteDogePost}
                      style={{color: 'red' }}
                      size='small'
                    >
                      <DeleteIcon />
                    </IconButton>
                  </Tooltip>
                </> : null}
                <Tooltip title='Show comments'>
                  <Button
                    variant='text'
                    onClick={() => setShowComments(show => !show)}
                  >
                    {`${comments.length} ${comments.length === 1 ? ' comment' : ' comments'}`}
                  </Button>
                </Tooltip>
              </Box>
            </Box>
          </div>
          <Menu
            anchorEl={reactionsListAnchorEl}
            keepMounted
            open={Boolean(reactionsListAnchorEl)}
            onClose={closeReactionsList}
          >
            {reactions.map(({ doge, type }) =>
              <MenuItem>
              {doge.firstname} {doge.lastname}: {type}
            </MenuItem>)}
          </Menu>
          <Menu
            id="simple-menu"
            anchorEl={reactionPickerAnchorEl}
            keepMounted
            open={Boolean(reactionPickerAnchorEl)}
            onClose={closeReactionPicker}
          >
            <MenuItem onClick={() => {
              closeReactionPicker()
              reactToDogePost("WOW")
            }}><NewReleasesIcon style={{ color: 'red' }} /><span style={{ marginLeft: '.5rem', textAlign: 'center', width: '100%' }}>WOW!</span></MenuItem>
            <MenuItem onClick={() => {
              closeReactionPicker()
              reactToDogePost("HAHA")
            }}><SentimentVerySatisfiedOutlinedIcon style={{ color: '#ff5722' }} /><span style={{ marginLeft: '.5rem', textAlign: 'center', width: '100%' }}>HAHA!</span></MenuItem>
            <MenuItem onClick={() => {
              closeReactionPicker()
              reactToDogePost("MUCH_SAD")
            }}><SentimentDissatisfiedIcon style={{ color: 'cornflowerblue' }} /><span style={{ marginLeft: '.5rem', textAlign: 'center', width: '100%' }}>MUCH SAD</span></MenuItem>
          </Menu>
          <Collapse in={showComments} timeout="auto" unmountOnExit>
            <Comments dogePost={dogePost} reloadDogePosts={reloadDogePosts} />
          </Collapse>
        </Container>
      </Card>
    </TimelineContent>
    <Snackbar open={errorMessage !== ''}  autoHideDuration={2000} onClose={() => setErrorMessage('')}>
      { /* @ts-ignore */ }
      <Alert onClose={() => setErrorMessage(false)} severity="error">{errorMessage.substr(0, 1000)}</Alert>
    </Snackbar>
    <Snackbar open={success}  autoHideDuration={6000} onClose={() => setSuccess(false)}>
      { /* @ts-ignore */ }
      <Alert onClose={() => setErrorMessage(false)} severity="success">Doge post deleted successfully</Alert>
    </Snackbar>
  </TimelineItem>

  function reactToDogePost(type: ReactionType) {
    fetch("/api/v1/posting/react", {
      method: "PATCH",
      headers: {
        Authorization: localStorage.getItem('token') || '',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        dogePostId,
        type
      })
    }).then(res => {
      if (res.ok)
        reloadDogePosts()
      else res.text().then(setErrorMessage)
    }).catch(({ message }) => setErrorMessage(message))
  }

  function deleteReaction() {
    fetch("/api/v1/posting/react", {
      method: "DELETE",
      headers: {
        Authorization: localStorage.getItem('token') || '',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        dogePostId
      })
    }).then(res => {
      if (res.ok)
        reloadDogePosts()
      else res.text().then(setErrorMessage)
    }).catch(({ message }) => setErrorMessage(message))
  }

  function deleteDogePost() {
    fetch("/api/v1/posting/delete-post", {
      method: "DELETE",
      headers: {
        Authorization: localStorage.getItem('token') || '',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ dogePostId })
    }).then(res => {
      if (res.ok) {
        reloadDogePosts()
        setSuccess(true)
      }
      else res.text().then(setErrorMessage)
    }).catch(({ message }) => setErrorMessage(message))
  }

  function doesSignedInDogeReacted() {
    return reactions.some(({ doge }) => doge.username === signedInDoge.username)
  }

  function getIconForReactionType(type: ReactionType|undefined) {
    if (type === "WOW") return <NewReleasesIcon  style={{ color: 'red' }}  />
    if (type === "HAHA") return <SentimentVerySatisfiedOutlinedIcon style={{ color: '#ff5722' }} />
    if (type === "MUCH_SAD") return <SentimentDissatisfiedIcon style={{ color: 'cornflowerblue' }}  />
  }
}