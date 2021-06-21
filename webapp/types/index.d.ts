interface Doge {
  firstname: string
  lastname: string
  username: string
}

interface DogePost {
  dogePostId: number
  doge: Doge
  content: string
  creationTime: Date
  comments: Comment[]
  reactions: Reaction[]
  edited: boolean
}

interface Comment {
  commentId: number
  doge: Doge
  content: string
  creationTime: Date
}

interface Reaction {
  reactionId: number
  doge: Doge
  type: ReactionType
}

type ReactionType = "WOW"|"HAHA"|"MUCH_SAD"

interface AddReaction {
  dogePostId: number
  type: ReactionType
  dogeUsername: string
}

interface DeletePost {
  dogePostId: number
}

type PostEditorRole = 'CREATE'|'EDIT'

interface DogePostEditingMode {
  currentContent: string
  dogePostId: number
}
