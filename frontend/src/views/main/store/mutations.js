export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	console.log(state, "이거")
	const keys = Object.keys(state.menus)
	console.log(keys)
	state.activeMenu = keys[index]
}

export function setHomeActive (state) {
	state.activeMenu = 'home'
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}
