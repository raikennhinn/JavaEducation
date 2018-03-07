package skillManagement.enumeration;

/**
 * 入力チェックを行った結果を表すフラグの列挙型
 */
public enum MemberBasicInfoErrorFlag {
	/**
	 * NoID：社員番号が未入力
	 */
	NoID,

	/**
	 * NoPW：PWが未入力
	 */
	NoPW,

	/**
	 * NoPW1：パスワード（再度）がが未入力
	 */
	NoPW1,

	/**
	 * NoPW2：パスワードとパスワード（再度）の入力内容が不一致
	 */
	NoPW2,

	/**
	 * NoAff：所属が未選択
	 */
	NoAff,

	/**
	 * NoName：氏名が未入力
	 */
	NoName,

	/**
	 * NoAuth：権限が未入力
	 */
	NoAuth,

	/**
	 * NoBoth：社員番号・PWの両方未入力
	 */
	NoBoth,

	/**
	 * IsNotHalfId：社員番号が全角
	 */
	IsNotHalfId,

	/**
	 * NoError：入力済み
	 */
	NoError
}


